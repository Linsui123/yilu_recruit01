package work0111;

import lombok.Data;

@Data
public class Result<T> {
    private boolean flag;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setFlag(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setFlag(false);
        result.setData(null);
        return result;
    }
}