package com.miniwallet.main.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class MiniWalletController {
	
	@PostMapping("/initializeAccount")
	public ResponseEntity<String> initializeAccount(@RequestParam(name = "customer_xid") String customer_xid) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("customer_xid", customer_xid).build();
		Request request = new Request.Builder().url("http://localhost:8080/api/v1/wallet").method("POST", body).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
	}

	@PostMapping("/enableWallet")
	private ResponseEntity<String> enableWallet(@RequestHeader(name = "authorization") String authorization) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url("http://localhost:8080/api/v1/wallet").method("POST", body)
				.addHeader("Authorization", authorization).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
	}

	@GetMapping("/viewWallet")
	private ResponseEntity<String> viewWallet(@RequestHeader(name = "Authorization") String authorization) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url("http://localhost:8082/api/v1/wallet").method("GET", null)
				.addHeader("Authorization", authorization).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
	}

	@PostMapping("/addVirtualMoney")
	private ResponseEntity<String> addVirtualMoney(@RequestParam(name = "reference_id") String reference_id, @RequestParam(name = "amount") String amount, 
			@RequestHeader(name = "Authorization") String authorization) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("amount", amount)
				.addFormDataPart("reference_id", reference_id).build();
		Request request = new Request.Builder().url("http://localhost:8082/api/v1/wallet/deposits").method("POST", body)
				.addHeader("Authorization", authorization).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
		
	}

	@PostMapping("/useVirtualMoney")
	private ResponseEntity<String> useVirtualMoney(@RequestParam(name = "reference_id") String reference_id, @RequestParam(name = "amount") String amount, 
			@RequestHeader(name = "Authorization") String authorization) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("amount", amount)
				.addFormDataPart("reference_id", reference_id).build();
		Request request = new Request.Builder().url("http://localhost:8082/api/v1/wallet/withdrawals").method("POST", body)
				.addHeader("Authorization", authorization).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
	}

	@PatchMapping("disableWallet")
	private ResponseEntity<String> disableWallet(@RequestParam(name = "is_disabled") String is_disabled, @RequestHeader(name = "Authorization") String authorization) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("is_disabled", is_disabled).build();
		Request request = new Request.Builder().url("http://localhost:8082/api/v1/wallet").method("PATCH", body)
				.addHeader("Authorization", authorization).build();
		Response response = client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(response.body().string());
	}
}
