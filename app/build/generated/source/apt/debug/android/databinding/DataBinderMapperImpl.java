
package android.databinding;
import com.karimrizk.triviapp.BR;
class DataBinderMapperImpl extends android.databinding.DataBinderMapper {
    public DataBinderMapperImpl() {
    }
    @Override
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.karimrizk.triviapp.R.layout.activity_category_chooser:
 {
                        final Object tag = view.getTag();
                        if(tag == null) throw new java.lang.RuntimeException("view must have a tag");
                    if ("layout/activity_category_chooser_0".equals(tag)) {
                            return new com.karimrizk.triviapp.databinding.ActivityCategoryChooserBinding(bindingComponent, view);
                    }
                        throw new java.lang.IllegalArgumentException("The tag for activity_category_chooser is invalid. Received: " + tag);
                }
                case com.karimrizk.triviapp.R.layout.activity_home:
 {
                        final Object tag = view.getTag();
                        if(tag == null) throw new java.lang.RuntimeException("view must have a tag");
                    if ("layout/activity_home_0".equals(tag)) {
                            return new com.karimrizk.triviapp.databinding.ActivityHomeBinding(bindingComponent, view);
                    }
                        throw new java.lang.IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
                }
                case com.karimrizk.triviapp.R.layout.activity_quiz:
 {
                        final Object tag = view.getTag();
                        if(tag == null) throw new java.lang.RuntimeException("view must have a tag");
                    if ("layout/activity_quiz_0".equals(tag)) {
                            return new com.karimrizk.triviapp.databinding.ActivityQuizBinding(bindingComponent, view);
                    }
                        throw new java.lang.IllegalArgumentException("The tag for activity_quiz is invalid. Received: " + tag);
                }
                case com.karimrizk.triviapp.R.layout.fragment_quiz:
 {
                        final Object tag = view.getTag();
                        if(tag == null) throw new java.lang.RuntimeException("view must have a tag");
                    if ("layout/fragment_quiz_0".equals(tag)) {
                            return new com.karimrizk.triviapp.databinding.FragmentQuizBinding(bindingComponent, view);
                    }
                        throw new java.lang.IllegalArgumentException("The tag for fragment_quiz is invalid. Received: " + tag);
                }
        }
        return null;
    }
    @Override
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    @Override
    public int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -183738890: {
                if(tag.equals("layout/activity_category_chooser_0")) {
                    return com.karimrizk.triviapp.R.layout.activity_category_chooser;
                }
                break;
            }
            case 293647131: {
                if(tag.equals("layout/activity_home_0")) {
                    return com.karimrizk.triviapp.R.layout.activity_home;
                }
                break;
            }
            case 556751633: {
                if(tag.equals("layout/activity_quiz_0")) {
                    return com.karimrizk.triviapp.R.layout.activity_quiz;
                }
                break;
            }
            case -852889424: {
                if(tag.equals("layout/fragment_quiz_0")) {
                    return com.karimrizk.triviapp.R.layout.fragment_quiz;
                }
                break;
            }
        }
        return 0;
    }
    @Override
    public String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}