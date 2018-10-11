package com.quiz.hritik.delete.Adapter_ViewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quiz.hritik.delete.R;

import java.util.List;

public class AfterQuizAnswerAdapter extends RecyclerView.Adapter<AfterQuizAnswerAdapter.ViewHolder> {

    private Context context;
    private List<String> questions;
    private List<String> answers;

    public AfterQuizAnswerAdapter(Context context, List<String> questions, List<String> answers) {
        this.context = context;
        this.questions = questions;
        this.answers = answers;
    }

    @NonNull
    @Override
    public AfterQuizAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_layout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AfterQuizAnswerAdapter.ViewHolder holder, int position) {

        holder.after_test_question.setText(questions.get(position));
        holder.after_test_answer.setText(answers.get(position));
        holder.quesNo.setText("Ans -" + String.valueOf(position + 1));


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView after_test_question, after_test_answer, quesNo;

        public ViewHolder(View itemView) {
            super(itemView);
            after_test_answer = itemView.findViewById(R.id.answers);
            after_test_question = itemView.findViewById(R.id.questions);
            quesNo = itemView.findViewById(R.id.quesNo);
        }
    }
}
