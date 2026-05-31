package ru.calorai.food.recognition.port.out;

public interface ImageSegmentationProvider {
    Long segment(byte[] imageBytes, String filename);
}
