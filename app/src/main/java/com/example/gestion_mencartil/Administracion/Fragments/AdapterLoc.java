package com.example.gestion_mencartil.Administracion.Fragments;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_mencartil.Administracion.Holder;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLoc extends  RecyclerView.Adapter<Holder>{
    private FirebaseUser firebaseUser = null;
    private FirebaseDatabase database = null;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
/*class adapter_myproducts : RecyclerView.Adapter<HolderProducts> {
    private var database: FirebaseDatabase? = null
    var firebaseUser: FirebaseUser? = null
    private var storage: FirebaseStorage? = null

    private var listProduct: ArrayList<Producto>
    val context: Context
    var  b : Bundle = Bundle.EMPTY
    private  var origenes :Int = 0

    constructor(context: Context) : super(){
        this.context = context
        listProduct= ArrayList()
    }

    constructor(listProduct: ArrayList<Producto>, context: Context, origen : Int) : super() {
        this.listProduct = listProduct
        this.context = context
        this.origenes = origen
        notifyItemInserted(listProduct.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderProducts {
        return  HolderProducts(LayoutInflater.from(context).inflate(R.layout.row_products, parent, false))
    }

    override fun onBindViewHolder(holder: HolderProducts, position: Int) {
        holder.name.setText(listProduct.get(position).name)
        holder.price.setText(listProduct.get(position).price)

        if(listProduct.get(position).photoUrl.equals("")){

        }else{
            Glide.with(context).load(listProduct.get(position).photoUrl).into(holder.image)
        }

        holder.image.setOnClickListener {
            val intent=Intent(context, PhotoIntent::class.java)
            intent.putExtra("imageURL", listProduct.get(position).photoUrl)
            startActivity(context,intent, Bundle())
        }

        holder.row.setOnClickListener{
            val intent=Intent(context, InfoProduct_Activity::class.java)
            intent.putExtra("imageURL", listProduct.get(position).photoUrl)
            intent.putExtra("name", listProduct.get(position).name)
            intent.putExtra("price", listProduct.get(position).price)
            intent.putExtra("description", listProduct.get(position).description)
            startActivity(context,intent,b)
        }

        if (origenes == 1){
            holder.imageButton.isVisible=true
        }else if(origenes == 2){
            holder.imageButton.isVisible=false
        }
            holder.imageButton.setOnClickListener{

                val alertDialogBu = AlertDialog.Builder(context)
                alertDialogBu.setTitle("Confirmas")
                alertDialogBu.setMessage("¿Estas seguro de querer borrar este producto?")
                alertDialogBu.setIcon(R.mipmap.ic_launcher_logo_round)
                alertDialogBu.setPositiveButton("Aceptar") { dialog, which ->
                    storage = FirebaseStorage.getInstance()
                    firebaseUser = FirebaseAuth.getInstance().currentUser
                    database = FirebaseDatabase.getInstance()//La base
                    database!!.getReference("Users")
                            .child("User")
                            .child(firebaseUser?.uid.toString())
                            .child("Products").child(listProduct.get(position).uidRandom).removeValue().addOnSuccessListener {
                                Toast.makeText(context,"Producto eliminado!!",Toast.LENGTH_LONG).show()

                                notifyDataSetChanged()

                                val i = Intent (context,Login_Activity::class.java)

                                if(listProduct.get(position).photoUrl.equals("")){

                                }else{
                                    storage!!.getReferenceFromUrl((listProduct.get(position).photoUrl)).delete()
                                }
                                startActivity(context,i, Bundle.EMPTY)
                            }.addOnFailureListener{
                                Toast.makeText(context,"No se a podido eliminar!!",Toast.LENGTH_LONG).show()
                            }

                }
                alertDialogBu.setNegativeButton("Cancelar") { dialog, which ->
                    Toast.makeText(context, "Has cancelado", Toast.LENGTH_SHORT).show()
                }
                val alertDialog = alertDialogBu.create()
                alertDialog.show()
            }
    }



    override fun getItemCount(): Int {
        return listProduct.size
    }
}*/