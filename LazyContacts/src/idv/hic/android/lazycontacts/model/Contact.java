package idv.hic.android.lazycontacts.model;

import java.io.InputStream;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;

public class Contact {

	private String rawId;
	private String id;

	private String sortKey;
	private String name;
	private List<String> phone;
	private List<String> email;
	private String contactType;
	private Bitmap photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) {
		this.phone = phone;
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public String getRawId() {
		return rawId;
	}

	public void setRawId(String rawId) {
		this.rawId = rawId;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactType() {
		return contactType;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	private static void readPhoto(Context context, Contact c) {

		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
				Long.parseLong(c.getId()));
		// Uri photoUri = Uri.withAppendedPath(contactUri,
		// Contacts.Photo.CONTENT_DIRECTORY);

		InputStream photoDataStream = Contacts.openContactPhotoInputStream(
				context.getContentResolver(), contactUri);

		if (photoDataStream != null) {
			Bitmap photo = BitmapFactory.decodeStream(photoDataStream);

			c.setPhoto(photo);
		}
	}

	public static void loadPhotoThread(Context context, Contact c) {

		class LoadPhotoRunnable implements Runnable {
			Context mContext;
			Contact mContact;

			public LoadPhotoRunnable(Context context, Contact c) {
				this.mContact = c;
				this.mContext = context;
			}

			public void run() {
				Contact.readPhoto(this.mContext, this.mContact);
			}

		}

		Runnable load = new LoadPhotoRunnable(context, c);

		Thread thread = new Thread(load);

		thread.start();

	}

}
