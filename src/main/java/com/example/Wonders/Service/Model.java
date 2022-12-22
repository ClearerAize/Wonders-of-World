package com.example.Wonders.Service;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Model {
    public float[] resizePic(String path) throws IOException {
        BufferedImage byteImage = ImageIO.read(new File(path)); //Грузим картинку
        //Меняем размер картинки на 180х180 так как это требует модель
        BufferedImage resizedImage = new BufferedImage(180, 180, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(byteImage, 0, 0, 180, 180, null); //Перерисовываем картинку
        //Преобразуем картинку в массив
        int[] pixels = resizedImage.getRGB(0, 0, 180, 180, null, 0, 180);
        //Преобразуем в float
        float[] floatPixels = new float[pixels.length * 3]; //3 канала (RGB) на каждый пиксель
        for (int i = 0; i < pixels.length; ++i) { //перебираем пиксели
            int pixel = pixels[i];
            floatPixels[i * 3] = (float) ((pixel >> 16) & 0xFF); //Red
            floatPixels[i * 3 + 1] = (float) ((pixel >> 8) & 0xFF); //Green
            floatPixels[i * 3 + 2] = (float) ((pixel) & 0xFF); //Blue
        }
        return floatPixels;
    }

    public String getAnswer(String path, SavedModelBundle model) throws IOException {
        float[] Picture = resizePic(path);
        //Создаем тензор
        Tensor<Float> tensor = Tensor.create(new long[]{1, 180, 180, 3}, FloatBuffer.wrap(Picture)); //1-количество картинок, 180-размер картинки, 3-количество каналов
        //Получаем результат
        Tensor<?> result = model.session()
                .runner()
                .feed("serving_default_Conv1_input:0", tensor) //Входной тензор
                .fetch("StatefulPartitionedCall:0") //Выходной тензор
                .run()
                .get(0);
        //Выводим результат в массив 1-12, так как всего 12 классов
        float[][] resultArray = new float[1][12];
        result.copyTo(resultArray);

        //Получаем индекс максимального значения
        int index = 0;
        float max = resultArray[0][0];
        for (int i = 0; i < resultArray[0].length; i++) {
            if (resultArray[0][i] > max) {
                max = resultArray[0][i];
                index = i;
            }
        }

        //Возвращаем результат
        System.out.println(resultArray[0][index]);


        for (int i = 0; i < resultArray[0].length; i++) {
            System.out.println(resultArray[0][i]);
        }

        switch (index) {
            case 0:
                return "burj_khalifa";
            case 1:
                return "chichen_itza";
            case 2:
                return "christ_the_reedemer";
            case 3:
                return "eiffel_tower";
            case 4:
                return "great_wall_of_china";
            case 5:
                return "machu_pichu";
            case 6:
                return "pyramids_of_giza";
            case 7:
                return "roman_colosseum";
            case 8:
                return "statue_of_liberty";
            case 9:
                return "stonehenge";
            case 10:
                return "taj_mahal";
            case 11:
                return "venezuela_angel_falls";
            default:
                return "Error";
        }
    }
}
