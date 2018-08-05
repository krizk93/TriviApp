package com.karimrizk.triviapp.databinding;
import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCategoryChooserBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_category_chooser, 1);
        sViewsWithIds.put(R.id.btn_category_general, 2);
        sViewsWithIds.put(R.id.btn_category_sports, 3);
        sViewsWithIds.put(R.id.btn_category_celebrities, 4);
        sViewsWithIds.put(R.id.btn_category_geography, 5);
        sViewsWithIds.put(R.id.btn_category_books, 6);
        sViewsWithIds.put(R.id.btn_category_film, 7);
        sViewsWithIds.put(R.id.progress_bar, 8);
    }
    // views
    @NonNull
    public final android.widget.Button btnCategoryBooks;
    @NonNull
    public final android.widget.Button btnCategoryCelebrities;
    @NonNull
    public final android.widget.Button btnCategoryFilm;
    @NonNull
    public final android.widget.Button btnCategoryGeneral;
    @NonNull
    public final android.widget.Button btnCategoryGeography;
    @NonNull
    public final android.widget.Button btnCategorySports;
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    @NonNull
    public final android.widget.ProgressBar progressBar;
    @NonNull
    public final android.widget.TextView tvCategoryChooser;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCategoryChooserBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.btnCategoryBooks = (android.widget.Button) bindings[6];
        this.btnCategoryCelebrities = (android.widget.Button) bindings[4];
        this.btnCategoryFilm = (android.widget.Button) bindings[7];
        this.btnCategoryGeneral = (android.widget.Button) bindings[2];
        this.btnCategoryGeography = (android.widget.Button) bindings[5];
        this.btnCategorySports = (android.widget.Button) bindings[3];
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progressBar = (android.widget.ProgressBar) bindings[8];
        this.tvCategoryChooser = (android.widget.TextView) bindings[1];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityCategoryChooserBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityCategoryChooserBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityCategoryChooserBinding>inflate(inflater, com.karimrizk.triviapp.R.layout.activity_category_chooser, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityCategoryChooserBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityCategoryChooserBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.karimrizk.triviapp.R.layout.activity_category_chooser, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityCategoryChooserBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityCategoryChooserBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_category_chooser_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityCategoryChooserBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}