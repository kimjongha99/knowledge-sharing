import {ResponseDto} from "../apis/dto/response/response";

type ResponseBody<T> = T | ResponseDto | null;

export type {
  ResponseBody,
}