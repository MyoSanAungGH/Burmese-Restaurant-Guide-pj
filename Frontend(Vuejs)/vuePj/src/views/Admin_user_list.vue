<template>
  <div>
    <v-row>
        <v-col cols="2">
            <admin_sidebar></admin_sidebar>
        </v-col>
               
            <v-col cols="10">
            <v-data-table
            :headers="headers"
            :items="userList"
            :items-per-page="4"
            class="elevation-1">

            <!-- <template v-slot:item.actions="{item}">
                <v-btn
                class="mr-2"
                color="red"
                fab
                x-small  
                elevation="2"
                @click="
                toDeleteUser = item;
                deleteDialog = true;"
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
            </template> -->
            </v-data-table>
        </v-col>
    </v-row>

    <v-dialog v-model="deleteDialog" width="500">
      <v-card hover elevation="-3">
        <v-toolbar color="primary" dark>
            <div class="text-h5" style="font-style: oblique;">Delete User</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <v-card-text class="pa-4">
          <v-row dense>
            <div class="text-h6 pb-6" style="font-family: serif; ">Are you sure to delete the user from table?</div>
            <v-col cols="6">ID: </v-col>
            <v-col cols="6" class="font-weight-bold">{{ toDeleteUser.id }}</v-col>
            <v-col cols="6">User name: </v-col>
            <v-col cols="6" class="font-weight-bold">{{ toDeleteUser.name }}</v-col>
            <v-col cols="6">Address : </v-col>
            <v-col cols="6" class="font-weight-bold">{{ toDeleteUser.address }}</v-col>
            <v-col cols="6">Status: </v-col>
            <v-col cols="6" class="font-weight-bold">{{ toDeleteUser.status }}</v-col>
            <v-col cols="6">User Gmail: </v-col>
            <v-col cols="6" class="font-weight-bold">{{ toDeleteUser.gmail }}</v-col>
          </v-row>
        </v-card-text>

        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteUser()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </div>
</template>

<script> 
import admin_sidebar from '@/components/admin_sidebar.vue'
import utils from '@/utils/utils';
export default {
    name:"admin_user_list",
    components: { admin_sidebar },

  data(){
    return{
        headers:[
            { text: "ID", value: "id", sortable: true },
            { text: "Name", value: "name", sortable: true },
            { text: "Age", value: "age", sortable: true },
            { text: "Address", value: "address", sortable: true },
            { text: "Gender", value: "gender", sortable: true },
            { text: "Role", value: "role", sortable: true },
            { text: "Status", value: "status", sortable: true },
            { text: "Gmail", value: "gmail", sortable: true },,
            { text: "Access Count", value: "accessCount", sortable: true },
            { text: "Start Join Date", value: "startJoinDate", sortable: true },
            { text: "UpdatedAt", value: "updatedDate", sortable: false },
            // { text: "Actions", value: "actions", sortable: false },
        ],


        deleteDialog: false,
        userList:[],
        toDeleteUser:{},
    } 
  },

  async created(){
    await this.fetchUser();
  },

  methods:{
    async fetchUser(){
        const resp = await utils.http.get("/admin/user");
        if(resp && resp.status === 200){
            const data = await resp.json();
            console.log(data)
            if(data){
                this.userList = data;
            }
        }
    },

    // async deleteUser(){
    //   const resp = await utils.http.del("")
    // }

}
}
</script>