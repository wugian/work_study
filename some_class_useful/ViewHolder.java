 public static <T extends View> T getAdapterView(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    } 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_feed_item, parent, false);
        }

        ImageView thumnailView = getAdapterView(convertView, R.id.video_thumbnail);
        ImageView avatarView =  getAdapterView(convertView, R.id.user_avatar);
        ImageView appIconView = getAdapterView(convertView, R.id.app_icon);
}