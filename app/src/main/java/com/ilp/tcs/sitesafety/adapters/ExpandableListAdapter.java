package com.ilp.tcs.sitesafety.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.listeners.MediaListener;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Image;
import com.ilp.tcs.sitesafety.views.ChildViewHolder;
import com.ilp.tcs.sitesafety.views.ParentViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  Modified by Abhishek Gupta on 29/8/2016.
 */
/**
 * Created by 1241575 on 5/31/2016.
 * ExpandableListAdapter used on main page
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter implements View.OnClickListener, View.OnLongClickListener {

    //==========nikhil======================================================================//
        HashMap<String,String> map = new HashMap<String, String>();
   /*ChildViewHolder LastHolder;
    ChildItem  itemex;*/
    private boolean firsttime = true;

    List<ChildItem> childItemsData = new ArrayList<>();

    //======================================================================================//


    /**
     * This String holds the name of Expandable list view
     */
    private static final String TAG = "aaaa"+ExpandableListAdapter.class.getSimpleName();

    /**
     * This variable holds the context of this adapter class
     */
    private final Context mContext;

    /**
     * This list variable holds the list of survey categories
     */
    private final ArrayList<GroupItem> mGroups;
    /**
     * This is a instance Media listener interface
     */
    private final MediaListener mListener;

    /**
     * This is the constructor of the Expandable List view
     *
     * @param context  context of the calling component
     * @param groups   Array of {@link GroupItem} to hold groups of Expandable list view
     * @param listener {@link MediaListener} used for gallery and camera media
     */
    public ExpandableListAdapter(Context context, ArrayList<GroupItem> groups, MediaListener listener) {
        this.mContext = context;
        this.mGroups = groups;
        this.mListener = listener;

    }

    @Override
    public int getGroupCount() {
        try {
            return mGroups.size();
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).getChildCount();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).getChildItem(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getGroup(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).getPointId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupItem groupItem = getGroup(groupPosition);
        ParentViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item_layout, parent, false);
            holder = new ParentViewHolder();
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_options);
            holder.tvGroupTitle = (TextView) convertView.findViewById(R.id.tvGroupTitle);
            holder.ivArrow = (ImageView) convertView.findViewById(R.id.ivArrow);
            holder.tvCounter = (TextView) convertView.findViewById(R.id.tvCounter);
            holder.tvGroupHeader = (TextView) convertView.findViewById(R.id.tvGroupHeader);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
          //  LastHolder = holder;
        }

        if (groupPosition == 0 || groupPosition == 9 || groupPosition == 20) {
            if (groupPosition == 0) {
                holder.tvGroupHeader.setText(R.string.building_external);
                holder.tvGroupHeader.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen2));
            } else if (groupPosition == 9) {
                holder.tvGroupHeader.setText(R.string.building_internal);
                holder.tvGroupHeader.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen3));
            } else {
                holder.tvGroupHeader.setText(R.string.process_practices);
                holder.tvGroupHeader.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen7));
            }
            holder.tvGroupHeader.setVisibility(View.VISIBLE);
        } else
            holder.tvGroupHeader.setVisibility(View.GONE);

        if (groupPosition < 9) {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen2));
        } else if (groupPosition < 20) {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen3));
        } else {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen7));
        }
        onBindGroupView(holder, isExpanded, convertView, groupItem);
        return convertView;
    }

    /**
     * This method is used to bind data to Group View in expandable list view
     *
     * @param holder      {@link ParentViewHolder} to hold the Views of parent in Expandable list view
     * @param isExpanded  whether the group is expanded or collapsed
     * @param convertView view to update
     * @param groupItem   {@link GroupItem} that is binding
     */
    private void onBindGroupView(ParentViewHolder holder, boolean isExpanded, View convertView, GroupItem groupItem) {
        holder.setExpanded(isExpanded);
        holder.setGroupTitle(groupItem.getTitle());
        holder.setCounter(groupItem.getCounter(), groupItem.getChildCount());
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       final ChildItem childItems = getChild(groupPosition, childPosition);
        final  EditText onclickEt ;

        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_layout, parent, false);
            holder = new ChildViewHolder(mContext);
            holder.tvChildTitle = (TextView) convertView.findViewById(R.id.tvChildTitle);
            holder.tvElement = (TextView) convertView.findViewById(R.id.tvElement);
            holder.rgResponse = (RadioGroup) convertView.findViewById(R.id.rgResponse);
            holder.rbYES = (RadioButton) convertView.findViewById(R.id.rbYES);
            holder.rbNO = (RadioButton) convertView.findViewById(R.id.rbNO);
            holder.rbNA = (RadioButton) convertView.findViewById(R.id.rbNA);
            holder.ibExpand = (ImageButton) convertView.findViewById(R.id.ibExpand);
            holder.ibSelectImage = (ImageButton) convertView.findViewById(R.id.ibSelectImage);
            holder.etDescription = (EditText) convertView.findViewById(R.id.etDescription);
            holder.llImage = (LinearLayout) convertView.findViewById(R.id.llImage);
            holder.llDescriptionView = (LinearLayout) convertView.findViewById(R.id.llDescriptionView);
            //       holder.etDescription.removeTextChangedListener(new DescriptionTypingListener(holder.etDescription));
            convertView.setTag(holder);

        } else {
            holder = (ChildViewHolder) convertView.getTag();
            //LastHolder = holder;
            onclickEt = (EditText) holder.etDescription;

          ChildItem  item = (ChildItem) holder.etDescription.getTag();
                childItemsData.add(item);
            if(item.getDescription()!=null)
                holder.etDescription.setText(item.getDescription());
//====================
            if(firsttime && item.getDescription() == null){
                onclickEt.setText("");
                holder.etDescription.setText("");
            }else {
               
                holder.etDescription.setText(item.getDescription());
            }

        }

            //=============nikhil==========================================================================================================
 /*       holder.etDescription.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
               childItems.setDescription(holder.etDescription.getText().toString().trim());
                if(!hasFocus){}
                   // childText.getTotal = holder.editText.getText().toString();
// In java variable contains reference of Object so when you change childText object, it will be reflected in same HashMap you are getting data from.

            }
        });*/
//========================================================================================================================================================
        //    holder.etDescription.removeTextChangedListener(new DescriptionTypingListener(holder.etDescription));
        //    holder.etDescription.addTextChangedListener(new DescriptionTypingListener(holder.etDescription));
        holder.etDescription.addTextChangedListener(new TextWatcher() {

            private EditText editText;


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /*if (firsttime && childItems.getDescription()== null){
                  //  firsttime = false;
                    editText =(EditText) holder.etDescription;
                    editText.setText("");
                    return ;
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //        Log.v("aaaaTextchange",charSequence.toString());
               ChildItem item =(ChildItem) holder.etDescription.getTag();
                item.setDescription(charSequence.toString());

             //   childItems.setDescription(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
              //  ImageButton imageButton = (ImageButton) holder.ibExpand;
             //=============

              //  final ChildItem itemex = (ChildItem) holder.ibExpand.getTag();
                if(firsttime){
                    firsttime = false;
                   // itemex.setExpanded(false);
                    //notifyDataSetChanged();

                    return;
                }

                //========================
              //  holder.ibExpand.clearFocus();

             /*   holder.ibExpand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){

                            itemex.setExpanded(false);
                            notifyDataSetChanged();
                        }
                    }
                });*/

                /*ChildItem item = (ChildItem) holder.etDescription.getTag();
                item.setDescription(editable.toString().trim());*/
                //     holder.etDescription.setText(item.getDescription());
            }
        });
        onBindChildView(holder, isLastChild, convertView, childItems);
        return convertView;
    }

    /**
     * This is used to bind data to the Child view of expandable list view
     *
     * @param holder      child view holder that is responsible for updating child view
     * @param isLastChild Whether the child is the last child within the group
     * @param convertView view to update
     * @param childItem   {@link ChildItem} that is binding
     */
    private void onBindChildView(final ChildViewHolder holder, boolean isLastChild, View convertView, final ChildItem childItem) {
//        DescriptionTypingListener listener = new DescriptionTypingListener(holder.getDescription());
       // itemex.setExpanded(false);

        holder.setChildTitle(childItem.getTitle());
        holder.setElement("Element :" + childItem.getElement());

//        Log.d("siteAdapter", childItem.getTitle() + ", " + childItem.getDescription());
        //  holder.etDescription.addTextChangedListener(new DescriptionTypingListener(holder.etDescription));
        // Removing check change listener. I will add it again after default works.
        // It must be removed as it creates problem in default view rendering in list.
        holder.groupResponse().setOnCheckedChangeListener(null);

        holder.markResponse(childItem.getResponse());
        holder.setExpanded(childItem.isExpanded());

        //holder.setDescription(childItem.getDescription());

        if (!childItem.getImages().isEmpty()) {
            holder.removeAllImages();
            for (Image image : childItem.getImages()) {
                holder.addImage(image);
            }
        } else {
            holder.removeAllImages();
        }

        holder.getIbExpand().setTag(childItem);
        holder.getIbExpand().setOnClickListener(this);

        holder.getIbSelectImage().setTag(childItem);
        holder.getIbSelectImage().setOnClickListener(this);

        holder.groupResponse().setTag(childItem);
        holder.groupResponse().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbYES: {
                        ChildItem item = (ChildItem) group.getTag();
                        item.setResponse(1);
                        item.setExpanded(false);
                        item.setIsResponseGiven(true);
                        holder.markResponse(1);
                        holder.setExpanded(false);
                        notifyDataSetChanged();
                    }
                    break;
                    case R.id.rbNA: {
                        ChildItem item = (ChildItem) group.getTag();
                        item.setResponse(0);
                        item.setIsResponseGiven(true);
                        item.setExpanded(false);
                        holder.markResponse(0);
                        holder.setExpanded(false);
                        notifyDataSetChanged();
                    }
                    break;
                    case R.id.rbNO: {
                        ChildItem item = (ChildItem) group.getTag();
                        item.setResponse(-1);
                        item.setIsResponseGiven(true);
                        item.setExpanded(true);
                        holder.setExpanded(true);
                        holder.markResponse(-1);
                        notifyDataSetChanged();
                    }
                    break;
                }
            }
        });
        holder.getDescription().setTag(childItem);
        holder.getDescription().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {

                ChildItem item = (ChildItem) v.getTag();


                  final  EditText desc = (EditText) v;
                if (!hasFocus) {

                    item.setDescription(desc.getText().toString());



            /*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        desc.setShowSoftInputOnFocus(false);
                    }
                    else {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }
            */    }

                else {



                    //desc.setText("");
                }
       if(firsttime || item.getDescription()==null){

           desc.setText("");


                        }else{
           desc.setText(item.getDescription());

                        }



            }
        });

//in the end

        holder.ibExpand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
             /*   LastHolder.setExpanded(false);
                if(hasFocus){

                    itemex.setExpanded(false);
                    notifyDataSetChanged();
                }*/
               // itemex.setExpanded(false);
                if(childItem.isExpanded()){

                    holder.setExpanded(false);
                    notifyDataSetChanged();
                }

            }
        });



//        holder.getDescription().addTextChangedListener(this);

        int imageCount = holder.getImageLayout().getChildCount();
        for (int i = 0; i < imageCount; i++) {
            ImageView iv = (ImageView) holder.getImageLayout().getChildAt(i);
            iv.setOnClickListener(this);
            iv.setOnLongClickListener(this);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibExpand:
                ChildItem item = (ChildItem) v.getTag();
               //=======changing button status

               //=============nikhil===

                item.setExpanded(!item.isExpanded());
               /* if(item.isExpanded() && item.getDescription() == null){
                    EditText editText  = (EditText) v;
                    editText.setText("");
                }*/
                notifyDataSetChanged();
             //   itemex =(ChildItem) v.getTag();
             //   itemex.setExpanded(!item.isExpanded());
                break;

            case R.id.ibSelectImage:
                PopupMenu popup = new PopupMenu(mContext, v);
                popup.getMenuInflater().inflate(R.menu.menu_add_media, popup.getMenu());

                ChildItem childItem = (ChildItem) v.getTag();

                PopUpMenuClickListener listener = new PopUpMenuClickListener(childItem);
                popup.setOnMenuItemClickListener(listener);
                popup.show();
                break;

            default:
                String path = (String) v.getTag(R.id.ivImageDisplay);
                String caption = (String) v.getTag(R.id.etCaption);
                Log.d(TAG, path);
                Log.d(TAG, caption + "");
                mListener.onUpdateImage(path, caption);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        // Allowing long click on the image to open it.
        // Calling simple click on long click and consuming the event.
        this.onClick(view);
        return true;
    }

    /**
     * This is the onclick listener method of popup menu
     * This method is used to add images from gallery and camera
     */
    private class PopUpMenuClickListener implements PopupMenu.OnMenuItemClickListener {

        /**
         * Child item that is showing Popup menu
         */
        private ChildItem childItem;

        public PopUpMenuClickListener(ChildItem childItem) {
            this.childItem = childItem;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_gallery) {
                mListener.onOpenGallery(childItem);
            } else if (item.getItemId() == R.id.action_camera) {
                mListener.onOpenCamera(childItem);
            }
            return true;
        }
    }

    private class DescriptionTypingListener implements TextWatcher {

        private EditText editText;
        private boolean firsttime = true;

        public DescriptionTypingListener(EditText editText) {
            this.editText = editText;
        }

        private String lastValue = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
            if (firsttime){
                return;
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
            /*
      //      Log.v("aaaaTextchangeCharSequ",charSequence.toString());
      //      Log.v("aaaaTextChageEdittextt",editText.getText().toString());
            String newValue = editText.getText().toString();
       //     Log.v("aaaaAfterTextEdittext",editText.getText().toString());
            //   Log.v("aaaaAfterNewValue",newValue );

            if(firsttime){
                firsttime = false;
          //      Log.v("aaaaAfterReturn",editText.getText().toString());
                return;
            }
            if(!newValue.equals(lastValue)){
                if(newValue.length()>=0){
                    ChildItem item = (ChildItem) editText.getTag();
                    item.setDescription(charSequence.toString());
                }

            }
            */
        }

        @Override
        public void afterTextChanged(Editable editable) {
       /*     if(firsttime){
                firsttime = false;
           //     Log.v("aaaaAfterReturn",editText.getText().toString());
                return;
            }
*/          editText.removeTextChangedListener(this);
        }
    }
}
