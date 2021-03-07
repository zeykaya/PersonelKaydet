package com.kaya.personelkaydet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kaya.personelkaydet.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var database=FirebaseDatabase.getInstance().reference// veritabanına veri yazıp okumak için
//veri ekleme işlemi;
        binding.btnEkle.setOnClickListener{
            var etNumber=binding.etNumber.text.toString().toInt()
            var etPersonName=binding.etPersonName.text.toString()
            var etPersonelMaas=binding.etPersonelMaas.text.toString().toInt()

          //  database.setValue(Personel(etNumber,etPersonName,etPersonelMaas)) //aynı kayıdın üzerine yeni kayıt yapar.
               database.child(etNumber.toString()).setValue(Personel(etPersonName,etPersonelMaas))
        }
//veri okuma işlemi
        var getdata = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var sb= StringBuilder()//metin birleştirme
                for (i in snapshot.children){
                    var adsoyad=i.child("padsoyad").getValue()
                    var maas=i.child("pmaas").getValue()
                    sb.append("${i.key}$adsoyad $maas $ \n")
                }

                binding.tvSonuc.setText(sb)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}