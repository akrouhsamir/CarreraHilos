package com.example.carrerahilos;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Corredor extends Thread{

    private ProgressBar progressBar;
    private Slider slider;
    private String nombre;
    private Double progreso;
    private Label ganador;
    public Corredor(String nombre,ProgressBar progressBar, Slider slider,Label ganador){
        this.progressBar = progressBar;
        this.slider = slider;
        this.nombre = nombre;
        this.ganador = ganador;
        progreso = 0.0;
    }

    @Override
    public void run() {
        do{
            this.setPriority((int)slider.getValue());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(progreso);
                }
            });
            getPrimos(1000000);
            progreso = BigDecimal.valueOf(progreso + 0.01).setScale(3, RoundingMode.HALF_UP).doubleValue();
            System.out.println(nombre + " --> "+this.getPriority());
        }while(progreso<1.001);
        if(CarreraControler.soyGanador()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ganador.setText("Gana " + nombre);
                }
            });
        }
    }

    public void getPrimos(int hasta){
        for(int i=1; i < hasta; i++){
            if(esPrimo(i)){
            }
        }
    }

    public boolean esPrimo(int a){
        boolean res = true;
        for(int i=2;i <= Math.sqrt(a) && res;i++){
            if(a%i == 0){
                res = false;
            }
        }
        return res;
    }
}
