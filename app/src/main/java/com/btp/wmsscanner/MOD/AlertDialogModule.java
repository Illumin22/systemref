package com.btp.wmsscanner.MOD;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlertDialogModule extends ContextWrapper {
    public AlertDialogModule(Context context){
        super(context);
    }

    public void sweet() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }

    public void Sweetalertwarning() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .show();
    }

    public void incompleteData() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Incomplete Data!")
                .setContentText("Please fill-up the required fields before proceeding.")
                .setConfirmText("OK")
                .show();
    }

    public void errorGeneral(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error!")
                .setContentText(message)
                .setConfirmText("OK")
                .show();
    }

    public void warningGeneral(String message){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText(message)
                .setConfirmText("OK")
                .show();
    }



    public void invalidQrCode(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Invalid/Damage Item Code")
                .setContentText("Invalid Item Code. Please check the validity of item code")
                .setConfirmText("OK")
                .show();
    }

    public void emptyQrCode(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Empty Item Code")
                .setContentText("Please re-enter the item code.")
                .setConfirmText("OK")
                .show();
    }

    public void duplicateQRCode(String lol){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText("Duplicate QR Code! " + lol)
                .setConfirmText("OK")
                .show();
    }

    public void duplicateCodeEncoded(String lol){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("New entry for " + lol + " has been encoded to the server.")
                .setConfirmText("OK")
                .show();
    }

    public void duplicateCodeNotEncoded(){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Info")
                .setContentText("New entry was not encoded to the server.")
                .setConfirmText("OK")
                .show();
    }

    public void itemQtyChanged(){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Item quantity has been changed.")
                .setConfirmText("OK")
                .show();
    }

    public void itemQtyNotChanged(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error changing quantity!")
                .setContentText("Unable to change quantity due to an error. /n" + message)
                .setConfirmText("OK")
                .show();
    }

    public void localhostSet(String message){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Localhost "+ message +"!")
                .setConfirmText("OK")
                .show();
    }

    public void localhostSetError(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Localhost "+ message +"!")
                .setConfirmText("OK")
                .show();
    }

    public void duplicateCodeError(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error!")
                .setContentText("Data was not encoded to the server due to an error. \n" + message)
                .setConfirmText("OK")
                .show();
    }


    public void infoGeneral(String message){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Info")
                .setContentText(message)
                .setConfirmText("OK")
                .show();
    }

    public void Sweetalertsuccess() {

        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .show();
    }
    public void Sweetalertcancellistener(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    public void Sweetalertprogress() {

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        //pDialog.setCancelable(false);
        pDialog.show();
    }
    public void Sweetalerterror() {

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }

}
