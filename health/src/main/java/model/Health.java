package model;

// 役割: 身長・体重からBMIと体型ラベルを計算して保持するシンプルなモデル
public class Health {
    // フィールド: 単位が分かるように命名
    private final double heightCm; // 身長(センチ)
    private final double weightKg; // 体重(キロ)
    private final double bmi;      // 計算済みBMI
    private final String bodyType; // 体型ラベル

    // コンストラクタ: 受け取った数値からBMI等を算出して不変にする
    public Health(double heightCm, double weightKg) {
        this.heightCm = heightCm; // 1) フィールドへ代入
        this.weightKg = weightKg; // 2) 同上
        this.bmi = calcBmi(heightCm, weightKg); // 3) BMIを計算
        this.bodyType = judgeType(this.bmi);    // 4) BMIから体型ラベルを決定
    }

    // メソッドの目的: BMI(kg/m^2)を返す純粋関数
    private double calcBmi(double heightCm, double weightKg) {
        double heightM = heightCm / 100.0;      // cm→mへ変換
        return weightKg / (heightM * heightM);  // 体重 ÷ 身長^2
    }

    // メソッドの目的: BMI値から体型カテゴリを定義
    private String judgeType(double bmi) {
        if (bmi < 18.5) return "やせ";
        if (bmi < 25.0) return "標準";
        if (bmi < 30.0) return "肥満(1度)";
        if (bmi < 35.0) return "肥満(2度)";
        if (bmi < 40.0) return "肥満(3度)";
        return "肥満(4度)";
    }

    // --- Getter: JSPから表示に使う ---
    public double getHeightCm() { return heightCm; } // 表示用: 身長(cm)
    public double getWeightKg() { return weightKg; } // 表示用: 体重(kg)
    public double getBmi()      { return bmi; }      // 表示用: BMI
    public String getBodyType() { return bodyType; } // 表示用: 体型ラベル
}
