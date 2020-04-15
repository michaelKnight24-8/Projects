import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolmanagementsystem.AssignmentsItem;
import com.example.schoolmanagementsystem.R;

import java.util.ArrayList;

public class MathAssignmentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_assignments);
        ArrayList<AssignmentsItem> itemsList = new ArrayList<>();
        itemsList.add(new AssignmentsItem("Finish problems 12-30", "12-12-2020"));
    }
}
