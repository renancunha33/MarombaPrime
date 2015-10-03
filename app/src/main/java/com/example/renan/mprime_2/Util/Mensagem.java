package com.example.renan.mprime_2.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Renan on 03/10/2015.
 */
public class Mensagem {
    public static void Msg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void addMsgOK(Activity activity, String titulo, String msg, int icone) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setNeutralButton("OK", null);
        alert.setIcon(icone);
        alert.show();

    }

    public static void addMsgConfirm(Activity activity, String titulo, String msg, int icone, View.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setPositiveButton("sim", null);
        alert.setNegativeButton("não", null);
        alert.setIcon(icone);
        alert.show();

    }

    public static AlertDialog CriarAlertDialog(Activity activity) {
        final CharSequence[] itens = {
                "Editar",
                "Excluir"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Opções");
        alert.setItems(itens, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }
    public static AlertDialog CriarDialogC0nfirmacao(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Deseja Realmente excluir?");
        alert.setPositiveButton("Sim", (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton("Não", (DialogInterface.OnClickListener) activity);
        return alert.create();
    }
}