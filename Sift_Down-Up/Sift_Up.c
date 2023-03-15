private void sift_up ( int pos ) {
        int aux;
        while((pos > 0) && (v[parent(pos)] < v[pos])){
            aux = v[pos];
            v[pos] = v[parent(pos)];
            v[parent(pos)] = aux;

            pos = parent(pos);
       }
    }