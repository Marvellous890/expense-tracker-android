package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.codeycoder.expensetracker.db.AppDatabase;
import com.codeycoder.expensetracker.db.Transaction;
import com.codeycoder.expensetracker.db.TransactionDao;
import com.codeycoder.expensetracker.views.Button;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TransactionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ENDPADDING = 1;
    private final Context context;
    private final ViewModelOwner viewModelOwner;
    private List<Transaction> data = new ArrayList<>();

    public ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                /*example:
                val sourcePosition = source.adapterPosition
               val targetPosition = target.adapterPosition

               Collections.swap(itemsArrayList,sourcePosition,targetPosition)
               myAdapter.notifyItemMoved(sourcePosition,targetPosition)
                * */

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                // delete from db
                ViewModel vm = viewModelOwner.getViewModel();
                if (vm instanceof HomeViewModel) {
                    HomeViewModel homeVm = (HomeViewModel) vm;
                    int position = viewHolder.getBindingAdapterPosition();
                    Transaction t = data.get(position);
                    homeVm.deleteTransaction(t);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "'" + t.getName() + "' deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }

            if (direction == ItemTouchHelper.RIGHT) {
                // edit
                Transaction t = data.get(viewHolder.getBindingAdapterPosition());
                HomeFragmentDirections.ActionHomeFragmentToAddTransFragment dir = HomeFragmentDirections.actionHomeFragmentToAddTransFragment(t.getId(), t.getName(), t.getAmount(), t.getDescription(), t.getTimeAdded());
                dir.setUpdating(true);
                if (context instanceof MainActivity) {
                    MainActivity ma = (MainActivity) context;
                    ma.navigateTo(dir);
                }
            }
        }

        public void onChildDraw (@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.grayscale_500))
                    .addSwipeRightActionIcon(R.drawable.write)
                    .setSwipeRightActionIconTint(0xFFFFFFFF)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.error_200))
                    .addSwipeLeftActionIcon(R.drawable.trash)
                    .setSwipeLeftActionIconTint(0xFFFFFFFF)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public ItemTouchHelper.Callback getItemTouchHelperCallBack() {
        return callback;
    }

    public TransactionListAdapter(Context context, ViewModelOwner viewModelOwner) {
        this.context = context;
        this.viewModelOwner = viewModelOwner;

        TransactionDao transactionDao = AppDatabase.Companion.getInstance(context).getTransactionDao();
        LiveData<List<Transaction>> transLiveData = transactionDao.getAll();
        transLiveData.observe((LifecycleOwner) context, transactions -> {
            if (transactions != null) {
                data = transactions;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        switch (viewType) {
            case TYPE_ENDPADDING:
                View v = new View(parent.getContext());
                v.setLayoutParams(new RecyclerView.LayoutParams(-1, dp(70)));
                root = v;
                break;
            case TYPE_ITEM:
            default:
                root = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
                break;
        }
        return new RecyclerView.ViewHolder(root) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            LinearLayout v = (LinearLayout) holder.itemView;
            Button b = v.findViewById(R.id.t_icon);
            b.setPadding(dp(0), dp(0), dp(0), dp(0));
            b.setIcon(v.getContext().getDrawable(R.drawable.coins_line));

            Transaction transaction = data.get(position);


            ((TextView) v.findViewById(R.id.trans_name)).setText(transaction.getName());
            ((TextView) v.findViewById(R.id.trans_cost)).setText(context.getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(transaction.getAmount())));
            ((TextView) v.findViewById(R.id.trans_desc)).setText(context.getString(R.string.main_account_today, Utilities.getFriendlyDateTime(transaction.getTimeAdded())));
            ((TextView) v.findViewById(R.id.trans_time)).setText(new SimpleDateFormat("hh:mm a").format(new Date(transaction.getTimeAdded())));
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) return TYPE_ENDPADDING;

        return TYPE_ITEM;
    }
}
