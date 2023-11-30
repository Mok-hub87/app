public class linklist {
    node head;
    class node{
        String data;
        node next;
        public node(String data) {this.data = data;}
    }

    public void add(String data) {
        if (head==null){
            head= new node(data);
        }
        else{
            node newnode = new node(data);
            newnode.next = head;
            head = newnode;
        }
    }

    public void remove() {
       if (head!=null){
           head = head.next;
       }
    }

    public boolean find(String data){
        node tempnode = head;
        while (tempnode!=null){
            if (tempnode.data.equals(data))
                return true;
            tempnode = tempnode.next;
        }
        return false;
    }

    public static void main(String[] args){
        linklist mylist = new linklist();
        mylist.add("a");
        mylist.add("b");
        mylist.add("c");
        System.out.println(mylist.head);
        System.out.println(mylist.head.data);
        System.out.println(mylist.head.next.data);
        System.out.println(mylist.find("a"));
    }
}
