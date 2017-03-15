package com.javacodegeeks.enterprise.rest.jersey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils; 
import javax.ws.rs.core.HttpHeaders;
import com.sun.jersey.core.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import javax.ws.rs.Produces;

@Path("/file")
public class transformCurrentPdf {

	@POST
	@Path("/upload")
	@Consumes( MediaType.MULTIPART_FORM_DATA)
	//@Produces("application/pdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

		System.out.println(">> transformCurrentPdf - uploadFile - fileDetail:" + fileDetail);
		
		String uploadedFileLocation = "/tmp/" + fileDetail.getFileName();

		// save it
		File resFile = writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File name : " + fileDetail.getFileName();
		output = output + "\n Size : " + fileDetail.getSize();

		/*
		javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((File) resFile);
	    responseBuilder.type("application/pdf");
	    responseBuilder.header("Content-Disposition", "filename=TEMPLATE_A1B.pdf");
	    return responseBuilder.build();
		*/
		/*
		javax.ws.rs.core.Response.ResponseBuilder response = Response.ok((Object) resFile);
		response.header("Content-Disposition", "attachment; filename=TEMPLATE_A1B.pdf");
		
		return response.build();
		*/
		//javax.ws.rs.core.Response.ResponseBuilder response = Response.ok((Object) resFile);
		try {
			
		javax.ws.rs.core.Response.ResponseBuilder response = Response.ok((Object) FileUtils.readFileToByteArray(resFile));
		response.header("Content-Disposition", "attachment; filename=TEMPLATE_A1B.pdf");
		return response.build();
		
		//Response.ok(FileUtils.readFileToByteArray(resFile)).type("application/pdf").header("Content-Disposition", "attachment; filename=TEMPLATE_A1B").build();

		}
		catch(Exception e) {
			
		}
		return null;
		//return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	
	private File writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			System.out.println(">> transformCurrentPdf - writeToFile - HERE0");
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			System.out.println(">> transformCurrentPdf - writeToFile - HERE1");
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			System.out.println(">> transformCurrentPdf - writeToFile - HERE2");
			return (new File(uploadedFileLocation));
			
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println(">> transformCurrentPdf - writeToFile - HERE ERR");
		}
		System.out.println(">> transformCurrentPdf - writeToFile - HERE3");
		return null;
	}
	
}

