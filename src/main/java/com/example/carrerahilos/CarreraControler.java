package com.example.carrerahilos;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class CarreraControler {
    private static boolean hayGanador;
    @FXML
    private Slider prioridadHilo1,prioridadHilo2,prioridadHilo3;

    @FXML
    private ProgressBar progressHilo1, progressHilo2, progressHilo3;

    @FXML
    private Label labelHilo1, labelHilo2, labelHilo3;

    @FXML
    private Label ganador;

    @FXML
    private TextField resultadoHilo1, resultadoHilo2, resultadoHilo3;

    private String [] comentarios = {"Quién ganara", "Como corren los corredores","Arranca la moto", "Fiuuumba","Espectucular esos hilos","La CPU esta que arde",
    "Así se ejecuta"};

    @FXML
    public void initialize(){
        initSlider(prioridadHilo1);
        initSlider(prioridadHilo2);
        initSlider(prioridadHilo3);

        labelHilo1.textProperty().bind(new StringBinding() {
            @Override
            protected String computeValue() {
                return "Prioridad: ";
            }
        }.concat(prioridadHilo1.valueProperty().asString()));

        labelHilo2.textProperty().bind(new StringBinding() {
            @Override
            protected String computeValue() {
                return "Prioridad: ";
            }
        }.concat(prioridadHilo2.valueProperty().asString()));

        labelHilo3.textProperty().bind(new StringBinding() {
            @Override
            protected String computeValue() {
                return "Prioridad: ";
            }
        }.concat(prioridadHilo3.valueProperty().asString()));

        progressHilo1.setProgress(0);
        progressHilo2.setProgress(0);
        progressHilo3.setProgress(0);

        bindProgressToField(progressHilo1,resultadoHilo1);
        bindProgressToField(progressHilo2,resultadoHilo2);
        bindProgressToField(progressHilo3,resultadoHilo3);
    }

    private void initSlider(Slider slider){
        slider.setShowTickLabels(true);
        slider.setMin(1);
        slider.setMax(10);
        slider.setMajorTickUnit(1);
        slider.valueProperty().addListener((obs,oldVal,newVal)->{
            slider.setValue(Math.round(newVal.doubleValue()));
        });
    }

    private void bindProgressToField(ProgressBar bar, TextField tf){
        IntegerProperty h1 = new SimpleIntegerProperty();
        h1.bind(bar.progressProperty().multiply(100));
        tf.textProperty().bind(h1.asString());
    }

    public static synchronized boolean soyGanador(){
        boolean resultado=false;
        if(!hayGanador){
            resultado=true;
            hayGanador=true;
        }
        return resultado;
    }

    @FXML
    public void comenzarCarrera(){
        ganador.setText("Comienza la carrera...");
        hayGanador=false;
        Corredor corr1 = new Corredor("HILO1",progressHilo1,prioridadHilo1,ganador);
        Corredor corr2 = new Corredor("HILO2",progressHilo2,prioridadHilo2,ganador);
        Corredor corr3 = new Corredor("HILO3",progressHilo3,prioridadHilo3,ganador);


        Thread com = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!hayGanador){
                    int rand = (int)(Math.random() * (comentarios.length-1));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(!hayGanador){
                                ganador.setText(comentarios[rand]);
                            }
                        }
                    });
                }
            }
        });
        com.start();
        corr1.start();
        corr2.start();
        corr3.start();
    }
}