package fr.utt.if26.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.utt.if26.model.User;

public class ContactFragment extends ListFragment {

	

		

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user = (User) getActivity().getIntent().getSerializableExtra("user");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,
				user.getStringContactList());
		setListAdapter(adapter);
	}
    
	
	

}
