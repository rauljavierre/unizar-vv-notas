package es.unizar.eina.categories;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.unizar.eina.notes.R;

public class CategoryIconAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryWithImage> lista;

    public CategoryIconAdapter(Context context, List<CategoryWithImage> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vista = convertView;
        LayoutInflater inflate = LayoutInflater.from(context);
        vista = inflate.inflate(R.layout.image_category, null);

        ImageView image = (ImageView) vista.findViewById(R.id.categoryImage);
        TextView categoryName = (TextView) vista.findViewById(R.id.categoryText);
        Log.d("d", "El valor de la imagen es: "+lista.get(position).getIcon());
        image.setImageResource(lista.get(position).getIcon());
        categoryName.setText(lista.get(position).getCategoryName());
        return vista;
    }

}
