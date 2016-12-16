package com.chenkovegor.gameofthrones.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenkovegor.gameofthrones.R;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.squareup.picasso.Picasso;
import com.chenkovegor.gameofthrones.data.storage.models.Character;

import java.util.List;

public class CharactersListAdapter extends BaseAdapter{

    private final static String TAG = ConstantManager.TAG_PREFIX;

    private List<Character> mCharacters;
    private Context mContext;
    private LayoutInflater mInflater;

    public CharactersListAdapter(List<Character> characters, Context context) {
        Log.d(TAG, "CharactersListAdapter: CharactersListAdapter");
        mCharacters = characters;
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mCharacters != null){
            return mCharacters.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mCharacters.get(i).getName();
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = mInflater.inflate(R.layout.item_character_list, parent, false);
        }

        TextView characterName = (TextView) itemView.findViewById(R.id.charaster_name_txt);
        characterName.setText(mCharacters.get(position).getName());
        TextView characterAliases = (TextView) itemView.findViewById(R.id.charaster_aliases_txt);
        characterAliases.setText(mCharacters.get(position).getAliases());

        ImageView emblem = (ImageView) itemView.findViewById(R.id.house_emblem_img);
        Integer emblemId = null;
        switch (mCharacters.get(position).getHouseName()){
            case "STARKS": emblemId = R.drawable.stark_icon;
                break;
            case "LANNISTERS": emblemId = R.drawable.lanister_icon;
                break;
            case "TARGARYENS": emblemId = R.drawable.targaryen_icon;
                break;
        }
        if(emblemId != null){
            Picasso.with(mContext)
                    .load(emblemId)
                    .into(emblem);
        }

        return itemView;
    }
}
