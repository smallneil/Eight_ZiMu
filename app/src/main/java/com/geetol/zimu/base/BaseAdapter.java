package com.geetol.zimu.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

abstract public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;
    protected ArrayList<ItemView> headers = new ArrayList<>();
    protected ArrayList<ItemView> footers = new ArrayList<>();
    protected List<T> mObjects;
    private Context mContext;


    public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{
        private int mMaxCount;
        public GridSpanSizeLookup(int maxCount){
            this.mMaxCount = maxCount;
        }
        @Override
        public int getSpanSize(int position) {
            if (headers.size()!=0){
                if (position<headers.size())return mMaxCount;
            }
            if (footers.size()!=0) {
                int i = position - headers.size() - mObjects.size();
                if (i >= 0) {
                    return mMaxCount;
                }
            }
            return 1;
        }
    }

    public GridSpanSizeLookup obtainGridSpanSizeLookUp(int maxCount){
        return new GridSpanSizeLookup(maxCount);
    }
    /**
     * Constructor
     *
     * @param context The current context.
     */
    public BaseAdapter(Context context) {
        init(context,  new ArrayList<T>());
    }


    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public BaseAdapter(Context context, T[] objects) {
        init(context, Arrays.asList(objects));
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public BaseAdapter(Context context, List<T> objects) {
        init(context, objects);
    }


    private void init(Context context , List<T> objects) {
        mContext = context;
        mObjects = new ArrayList<>(objects);
    }

    public void addHeader(ItemView view){
        if (view==null)
            return;
        headers.add(view);
        notifyItemInserted(headers.size()-1);
    }

    public void addFooter(ItemView view){
        if (view==null)
            return;
        footers.add(view);
        notifyItemInserted(headers.size()+getCount()+footers.size()-1);
    }

    public void removeAllHeader(){
        int count = headers.size();
        headers.clear();
        notifyItemRangeRemoved(0,count);
    }

    public void removeAllFooter(){
        int count = footers.size();
        footers.clear();
        notifyItemRangeRemoved(headers.size()+getCount(),count);
    }

    public ItemView getHeader(int index){
        return headers.get(index);
    }

    public ItemView getFooter(int index){
        return footers.get(index);
    }

    public int getHeaderCount(){return headers.size();}

    public int getFooterCount(){return footers.size();}

    public void removeHeader(ItemView view){
        int position = headers.indexOf(view);
        headers.remove(view);
        notifyItemRemoved(position);
    }

    public void removeFooter(ItemView view){
        int position = headers.size()+getCount()+footers.indexOf(view);
        footers.remove(view);
        notifyItemRemoved(position);
    }

    /**
     * 应该使用这个获取item个数
     * @return
     */
    public int getCount(){
        return mObjects.size();
    }

    public void add(T object) {
        if (object!=null){
            mObjects.add(object);
        }
        notifyItemInserted(headers.size()+getCount());
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection!=null&&collection.size()!=0){
            mObjects.addAll(collection);
        }
        int dataCount = collection==null?0:collection.size();
        notifyItemRangeInserted(headers.size()+getCount()-dataCount,dataCount);
    }

    public void addAll(T[] items) {
        if (items!=null&&items.length!=0) {
            Collections.addAll(mObjects, items);
        }
        int dataCount = items==null?0:items.length;
        notifyItemRangeInserted(headers.size()+getCount()-dataCount,dataCount);
    }

    public void insert(T object, int index) {
        mObjects.add(index, object);
        notifyItemInserted(headers.size()+index);
    }

    public void insertAll(T[] object, int index) {
        mObjects.addAll(index, Arrays.asList(object));
        int dataCount = object==null?0:object.length;
        notifyItemRangeInserted(headers.size()+index,dataCount);
    }

    public void insertAll(Collection<? extends T> object, int index) {
        mObjects.addAll(index, object);
        int dataCount = object==null?0:object.size();
        notifyItemRangeInserted(headers.size()+index,dataCount);
    }

    public void update(T object,int pos){
        mObjects.set(pos,object);
        notifyItemChanged(pos);
    }

    public void remove(T object) {
        int position = mObjects.indexOf(object);
            if (mObjects.remove(object)){
                notifyItemRemoved(headers.size()+position);
            }
    }

    public void remove(int position) {
        mObjects.remove(position);
        notifyItemRemoved(headers.size()+position);
    }

    public void removeAll() {
        int count = mObjects.size();
        mObjects.clear();
        notifyItemRangeRemoved(headers.size(),count);
    }

    /**
     * 触发清空
     */
    public void clear() {
        mObjects.clear();
        notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *        in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(mObjects, comparator);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = createSpViewByType(parent, viewType);
        if (view!=null){
            return new StateViewHolder(view);
        }

        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType);

        //itemView 的点击事件
        if (mItemClickListener!=null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(viewHolder.getAdapterPosition()-headers.size());
                }
            });
        }

        if (mItemLongClickListener!=null){
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(viewHolder.getAdapterPosition()-headers.size());
                }
            });
        }
        return viewHolder;
    }

    abstract public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        if (headers.size()!=0 && position<headers.size()){
            headers.get(position).onBindView(holder.itemView);
            return ;
        }

        int i = position - headers.size() - mObjects.size();
        if (footers.size()!=0 && i>=0){
            footers.get(i).onBindView(holder.itemView);
            return ;
        }
        OnBindViewHolder(holder,position-headers.size());
    }

    public void OnBindViewHolder(BaseViewHolder holder, final int position){
        holder.setData(getItem(position));
    }

    @Deprecated
    @Override
    public final int getItemViewType(int position) {
        if (headers.size()!=0){
            if (position<headers.size())return headers.get(position).hashCode();
        }
        if (footers.size()!=0){
            int i = position - headers.size() - mObjects.size();
            if (i >= 0){
                return footers.get(i).hashCode();
            }
        }
        return getViewType(position-headers.size());
    }

    public int getViewType(int position){
        return 0;
    }


    public List<T> getAllData(){
        return new ArrayList<>(mObjects);
    }

    /**
     * {@inheritDoc}
     */
    public T getItem(int position) {
        return mObjects.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     *
     * @return The position of the specified item.
     */
    public int getPosition(T item) {
        return mObjects.indexOf(item);
    }

    /**
     * {@inheritDoc}
     */
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mObjects.size()+headers.size()+footers.size();
    }


    private class StateViewHolder extends BaseViewHolder {
        public StateViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ItemView {
        View onCreateView(ViewGroup parent);
        void onBindView(View headerView);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    private View createSpViewByType(ViewGroup parent, int viewType){
        for (ItemView headerView:headers){
            if (headerView.hashCode() == viewType){
                View view = headerView.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        for (ItemView footerview:footers){
            if (footerview.hashCode() == viewType){
                View view = footerview.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        return null;
    }
}
