package com.karimrizk.triviapp.databinding;
import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentQuizBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.question_cardview, 1);
        sViewsWithIds.put(R.id.txt_question, 2);
        sViewsWithIds.put(R.id.btn_answer1, 3);
        sViewsWithIds.put(R.id.btn_answer2, 4);
        sViewsWithIds.put(R.id.btn_answer3, 5);
        sViewsWithIds.put(R.id.btn_answer4, 6);
    }
    // views
    @NonNull
    public final android.widget.Button btnAnswer1;
    @NonNull
    public final android.widget.Button btnAnswer2;
    @NonNull
    public final android.widget.Button btnAnswer3;
    @NonNull
    public final android.widget.Button btnAnswer4;
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    @NonNull
    public final android.support.v7.widget.CardView questionCardview;
    @NonNull
    public final android.widget.TextView txtQuestion;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentQuizBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.btnAnswer1 = (android.widget.Button) bindings[3];
        this.btnAnswer2 = (android.widget.Button) bindings[4];
        this.btnAnswer3 = (android.widget.Button) bindings[5];
        this.btnAnswer4 = (android.widget.Button) bindings[6];
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.questionCardview = (android.support.v7.widget.CardView) bindings[1];
        this.txtQuestion = (android.widget.TextView) bindings[2];
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
    public static FragmentQuizBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentQuizBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<FragmentQuizBinding>inflate(inflater, com.karimrizk.triviapp.R.layout.fragment_quiz, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static FragmentQuizBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentQuizBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.karimrizk.triviapp.R.layout.fragment_quiz, null, false), bindingComponent);
    }
    @NonNull
    public static FragmentQuizBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentQuizBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/fragment_quiz_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new FragmentQuizBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}