package com.karimrizk.triviapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.karimrizk.triviapp.databinding.AnswerItemBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public AnswersAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        AnswerItemBinding itemBinding = AnswerItemBinding.inflate(inflater, parent, false);
        return new AnswersViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        if (mCursor != null) {
            if (!mCursor.moveToPosition(position))
                return;
            String questionNumber = mCursor.getString(mCursor.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_ID));
            String question = mCursor.getString(mCursor.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_QUESTION));
            question = questionNumber + "-" + question;
            String correctAnswer = mCursor.getString(mCursor.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER));
            String userAnswer = mCursor.getString(mCursor.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER));

            holder.binding.itemQuestion.setText(question);
            holder.binding.itemCorrectAnswer.setText(correctAnswer);
            holder.binding.itemUserAnswer.setText(userAnswer);

        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else return 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class AnswersViewHolder extends RecyclerView.ViewHolder {

        private final AnswerItemBinding binding;

        private AnswersViewHolder(AnswerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
