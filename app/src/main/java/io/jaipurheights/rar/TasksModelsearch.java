/**
 * Copyright (c) 2015 Cloudant, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package io.jaipurheights.rar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

//import com.cloudant.sync.datastore.BasicDocumentRevision;
import com.cloudant.sync.datastore.ConflictException;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentBodyFactory;
import com.cloudant.sync.datastore.DocumentException;
import com.cloudant.sync.datastore.DocumentRevision;
//import com.cloudant.sync.datastore.MutableDocumentRevision;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.query.QueryResult;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;
import com.google.common.eventbus.Subscribe;
import com.cloudant.sync.query.IndexManager;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Handles dealing with the datastore and replication.</p>
 */
class TasksModelsearch {

    private static final String LOG_TAG = "TasksModel";

    private static final String DATASTORE_MANGER_DIR = "data";
    private static final String TASKS_DATASTORE_NAME = "tasks";

    private Datastore mDatastore;

    private Replicator mPushReplicator;
    private Replicator mPullReplicator;

    private final Context mContext;
    private final Handler mHandler;

    private search searchListener;

    public TasksModelsearch(Context context) {

        this.mContext = context;

        // Set up our tasks datastore within its own folder in the applications
        // data directory.
        File path = this.mContext.getApplicationContext().getDir(
                DATASTORE_MANGER_DIR,
                Context.MODE_PRIVATE
        );
        DatastoreManager manager = new DatastoreManager(path.getAbsolutePath());
        try {
            this.mDatastore = manager.openDatastore(TASKS_DATASTORE_NAME);
        } catch (DatastoreNotCreatedException dnce) {
            Log.e(LOG_TAG, "Unable to open Datastore", dnce);
        }

        Log.d(LOG_TAG, "Set up database at " + path.getAbsolutePath());

        // Set up the replicator objects from the app's settings.
        try {
            this.reloadReplicationSettings();
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Unable to construct remote URI from configuration", e);
        }

        // Allow us to switch code called by the ReplicationListener into
        // the main thread so the UI can update safely.
        this.mHandler = new Handler(Looper.getMainLooper());

        Log.d(LOG_TAG, "TasksModel set up " + path.getAbsolutePath());
    }

    //
    // GETTERS AND SETTERS
    //

    /**
     * Sets the listener for replication callbacks as a weak reference.
     * @param listener {@link Postproperty} to receive callbacks.
     */

    public void setReplicationListener(search listener) {
        this.searchListener = listener;
    }
    //
    // DOCUMENT CRUD
    //

    /**
     * Creates a task, assigning an ID.
     * @param task task to create
     * @return new revision of the document
     */
   /* public Task createDocument(Task task) {
        MutableDocumentRevision rev = new MutableDocumentRevision();
        rev.body = DocumentBodyFactory.create(task.asMap());
        try {
            BasicDocumentRevision created = this.mDatastore.createDocumentFromRevision(rev);
            return Task.fromRevision(created);
        } catch (DocumentException de) {
            return null;
        }
    }
*/
    /**
     * Updates a Task document within the datastore.
     * @param task task to update
     * @return the updated revision of the Task
     * @throws ConflictException if the task passed in has a rev which doesn't
     *      match the current rev in the datastore.
     */
/*    public Task updateDocument(Task task) throws ConflictException {
        MutableDocumentRevision rev = task.getDocumentRevision().mutableCopy();
        rev.body = DocumentBodyFactory.create(task.asMap());
        try {
            BasicDocumentRevision updated = this.mDatastore.updateDocumentFromRevision(rev);
            return Task.fromRevision(updated);
        } catch (DocumentException de) {
            return null;
        }
    }
*/
    /**
     * Deletes a Task document within the datastore.
     * @param task task to delete
     * @throws ConflictException if the task passed in has a rev which doesn't
     *      match the current rev in the datastore.
     */
    public void deleteDocument(Task task) throws ConflictException {
       // this.mDatastore.deleteDocumentFromRevision(task.getDocumentRevision());
    }

    /**
     *<p>Returns all {@code Task} documents in the datastore.</p>
     */
    public List<Task> searchTasks(String description,String subdescription,String location,String city,String name,String Area,String Formtype) {
        int nDocs = this.mDatastore.getDocumentCount();
        List<DocumentRevision> all = this.mDatastore.getAllDocuments(0, nDocs, true);
        List<Task> tasks = new ArrayList<Task>();

        // Filter all documents down to those of type Task.
        IndexManager im = new IndexManager(mDatastore);
        List<Object> indexFields = new ArrayList<Object>();
        indexFields.add("description");
        indexFields.add("completed");
        indexFields.add("name");
        indexFields.add("phone");
        indexFields.add("Address");
        indexFields.add("subdescription");
        indexFields.add("location");
        indexFields.add("city");
        indexFields.add("price");
        indexFields.add("Area");
        indexFields.add("Information");
        indexFields.add("imagename");
        indexFields.add("Formtype");
// Create the index
        im.ensureIndexed(indexFields,"description");
        Map<String, Object> query = new HashMap<String, Object>();
  /*      Map<String, Object> gt30 = new HashMap<String, Object>();
        Map<String, Object> lt30 = new HashMap<String, Object>();
        Map<String, Object> area1 = new HashMap<String, Object>();
        Map<String, Object> area2 = new HashMap<String, Object>();
        if(price!=null && Area!=null) {
            gt30.put("$lt", Integer.parseInt(price));
            lt30.put("$gt",  (100));
            area1.put("price", gt30);
            area2.put("price", lt30);
            query.put("$and", Arrays.<Object>asList(area1, area2));
        }

  */
     /*   if(Area!=null)
        lt30.put("$gt",  Integer.parseInt(Area));
        */
       // query.put("$and", Arrays.<Object>asList(gt30, lt30));
        if(description!=null)
            query.put("name", name);
        if(description!=null)
        query.put("description", description);
        if(subdescription!=null)
            query.put("subdescription", subdescription);
        if(Formtype!=null)
            query.put("Formtype", Formtype);

    /*    if(location!=null)
            query.put("location", location);*/
        if(city!=null)
            query.put("city", city);



       if(location!=null&&location!=""&&!location.isEmpty()) {
           System.out.println("bug::::::::::::::::"+location+"::::::::::");
           query.put("location", location);
       }
    /*    if(price!=null)
            query.put("price",price);
        if(Area!=null)
            query.put("Area",Area);

            Map<String, Object> gt30 = new HashMap<String, Object>();
 gt30.put("$gt", 30);
 ageClause.put("age", gt30);
 Map<String, Object> eqMike = new HashMap<String, Object>();
 eqMike.put("$eq", "mike");
 nameClause.put("name", eqMike);
 andClause.put("$and", Arrays.<Object>asList(ageClause, nameClause));
 query.put("$or", Arrays.<Object>asList(petClause, andClause));
 */
        QueryResult result = im.find(query);

for(DocumentRevision revi:result)
{
    System.out.println(revi.getId()+"hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
}
        for(DocumentRevision rev :result) {

            Task t = Task.fromRevision(rev);
            if (t != null) {

                tasks.add(t);

            }
        }

        return tasks;
    }

    public List<Task> allTasks() {
        int nDocs = this.mDatastore.getDocumentCount();
        List<DocumentRevision> all = this.mDatastore.getAllDocuments(0, nDocs, true);

        List<Task> tasks = new ArrayList<Task>();

        // Filter all documents down to those of type Task.
   /*     IndexManager im = new IndexManager(mDatastore);
        List<Object> indexFields = new ArrayList<Object>();
        indexFields.add("description");
        indexFields.add("completed");
        indexFields.add("name");
        indexFields.add("phone");
        indexFields.add("Address");
        indexFields.add("subdescription");
        indexFields.add("location");
        indexFields.add("city");
        indexFields.add("price");
        indexFields.add("Area");
        indexFields.add("Information");
        indexFields.add("imagename");

// Create the index
        im.ensureIndexed(indexFields,"description");
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("description", "hs");
        QueryResult result = im.find(query);

        for(DocumentRevision revi:result)
        {
            System.out.println(revi.getId()+"hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        }

        */
        for(DocumentRevision rev :all) {

            Task t = Task.fromRevision(rev);
            if (t != null) {

                tasks.add(t);

            }
        }

        return tasks;
    }

    //
    // MANAGE REPLICATIONS
    //

    /**
     * <p>Stops running replications.</p>
     *
     * <p>The stop() methods stops the replications asynchronously, see the
     * replicator docs for more information.</p>
     */
    public void stopAllReplications() {
        if (this.mPullReplicator != null) {
            this.mPullReplicator.stop();
        }
        if (this.mPushReplicator != null) {
            this.mPushReplicator.stop();
        }
    }

    /**
     * <p>Starts the configured push replication.</p>
     */


    public void startPushReplication() {
        if (this.mPushReplicator != null) {
            this.mPushReplicator.start();
        } else {
            throw new RuntimeException("Push replication not set up correctly");
        }
    }

    /**
     * <p>Starts the configured pull replication.</p>
     */
    public void startPullReplication() {
        if (this.mPullReplicator != null) {
            this.mPullReplicator.start();
        } else {
            throw new RuntimeException("Push replication not set up correctly");
        }
    }

    /**
     * <p>Stops running replications and reloads the replication settings from
     * the app's preferences.</p>
     */
    public void reloadReplicationSettings()
            throws URISyntaxException {

        // Stop running replications before reloading the replication
        // settings.
        // The stop() method instructs the replicator to stop ongoing
        // processes, and to stop making changes to the datastore. Therefore,
        // we don't clear the listeners because their complete() methods
        // still need to be called once the replications have stopped
        // for the UI to be updated correctly with any changes made before
        // the replication was stopped.
        this.stopAllReplications();

        // Set up the new replicator objects
        URI uri = this.createServerURI();

        mPullReplicator = ReplicatorBuilder.pull().to(mDatastore).from(uri).build();
        mPushReplicator = ReplicatorBuilder.push().from(mDatastore).to(uri).build();

        mPushReplicator.getEventBus().register(this);
        mPullReplicator.getEventBus().register(this);

        Log.d(LOG_TAG, "Set up replicators for URI:" + uri.toString());
    }

    /**
     * <p>Returns the URI for the remote database, based on the app's
     * configuration.</p>
     * @return the remote database's URI
     * @throws URISyntaxException if the settings give an invalid URI
     */
    private URI createServerURI()
            throws URISyntaxException {
        // We store this in plain text for the purposes of simple demonstration,
        // you might want to use something more secure.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String username = sharedPref.getString(Postproperty.SETTINGS_CLOUDANT_USER, "");
        String dbName = sharedPref.getString(Postproperty.SETTINGS_CLOUDANT_DB, "");
        String apiKey = sharedPref.getString(Postproperty.SETTINGS_CLOUDANT_API_KEY, "");
        String apiSecret = sharedPref.getString(Postproperty.SETTINGS_CLOUDANT_API_SECRET, "");
        String host = username + ".cloudant.com";

        // We recommend always using HTTPS to talk to Cloudant.
        return new URI("https", apiKey + ":" + apiSecret, host, 443, "/" + dbName, null, null);
    }


    //
    // REPLICATIONLISTENER IMPLEMENTATION
    //

    /**
     * Calls the TodoActivity's replicationComplete method on the main thread,
     * as the complete() callback will probably come from a replicator worker
     * thread.
     */
    @Subscribe
    public void complete(ReplicationCompleted rc) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if (searchListener != null) {
                    searchListener.replicationComplete();
                }
            }
        });
    }

    /**
     * Calls the TodoActivity's replicationComplete method on the main thread,
     * as the error() callback will probably come from a replicator worker
     * thread.
     */
    @Subscribe
    public void error(ReplicationErrored re) {
        Log.e(LOG_TAG, "Replication error:", re.errorInfo.getException());
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if (searchListener != null) {
                    searchListener.replicationError();
                }
            }
        });
    }
}
