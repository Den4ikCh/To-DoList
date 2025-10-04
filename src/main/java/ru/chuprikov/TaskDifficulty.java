package ru.chuprikov;

public enum TaskDifficulty {
    HARD,
    MEDIUM,
    EASY;

    @Override
    public String toString() {
        switch (this) {
            case HARD:
                return "Сложная";
            case MEDIUM:
                return "Средняя";
            default:
                return "Лёгкая";
        }
    }
}
