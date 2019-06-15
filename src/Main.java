import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();

        //Valores para cada tipo de iris
        int set = 0;
        int ver = 1;
        int vir = 2;
        //Matris de confusão 3x3
        int matrizConfusao[][] = new int[3][3];
        //Carrega a lista de 150 iris pelo arquivo iris.arff
        BufferedReader br = new BufferedReader(new FileReader("src/resource/iris.arff"));
        while(br.ready()){
            String linha = br.readLine();
            if (linha.contains("Iris-setosa")) {
                linha = linha.replaceAll("Iris-setosa", String.valueOf(set));
            } else if (linha.contains("Iris-versicolor")){
                linha = linha.replaceAll("Iris-versicolor", String.valueOf(ver));
            } else {
                linha = linha.replaceAll("Iris-virginica", String.valueOf(vir));
            }
            list.add(linha);
        }
        br.close();
        int i = 0;
        for (String s : list){
            i = i + 1;
            System.out.println(s + "-" + i);
        }

        //Valor da distancia
        double distancia = 1.000;
        double distanciaAux = 0.0;
        int irisTypeBase = 0;
        int irisTypeTreino = 0;
        int acertos = 0;
        //Percorre os 30 primeiros da lista
        for (int x = 0; x < 30; x++){
            //Percorre os 150 da lista
            for (int y  = 0; y < list.size(); y ++){
                if (!list.get(x).equals(list.get(y))){
                    //Chama a função calculaDistancia e pega o valor da distancia
                    distanciaAux = calculaDistancia(list.get(x), list.get(y));
                    if (distanciaAux <= distancia){
                        //Recupera o tipo da iris
                        irisTypeBase = Integer.parseInt(list.get(y).substring(list.get(y).length()-1));
                        irisTypeTreino = Integer.parseInt(list.get(x).substring(list.get(x).length()-1));
                        distancia = distanciaAux;
                    }
                }
            }
            //Se tipos das iris forem iguais soma + 1 acerto
            if (irisTypeTreino == irisTypeBase){
                acertos = acertos + 1;
            }
            //Soma o tipo da iris na matriz de confusão
            matrizConfusao[irisTypeTreino][irisTypeBase] ++;
        }

        //Imprime a matriz de confusão
        System.out.println(matrizConfusao[0][0] + " " + matrizConfusao[0][1] + " " + matrizConfusao[0][2]);
        System.out.println(matrizConfusao[1][0] + " " + matrizConfusao[1][1] + " " + matrizConfusao[1][2]);
        System.out.println(matrizConfusao[2][0] + " " + matrizConfusao[2][1] + " " + matrizConfusao[2][2]);
        System.out.println("Acertos = " + acertos);

    }

    //Função que aplica a formula de KNN e retorna a sua distancia
    public static double calculaDistancia(String treino, String base){
        double distancia = 0.0;

        if (treino != null && base != null){
            String[] treinoAux = treino.split(",");
            String[] baseAux = base.split(",");
            distancia = Math.sqrt( Math.pow(Double.parseDouble(treinoAux[0]) - Double.parseDouble(baseAux[0]), 2) +
                    Math.pow(Double.parseDouble(treinoAux[1]) - Double.parseDouble(baseAux[1]), 2) +
                    Math.pow(Double.parseDouble(treinoAux[2]) - Double.parseDouble(baseAux[2]), 2) +
                    Math.pow(Double.parseDouble(treinoAux[3]) - Double.parseDouble(baseAux[3]), 2));
        }

        return distancia;
    }
}
