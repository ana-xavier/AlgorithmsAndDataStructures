 private void sift_down ( int pos ) {
        int max = pos;
        int esq = ((2*pos)+1);
        int dir = ((2*pos)+2);

        if((esq <= used) && (v[esq] > v[max])){
             max = esq;
        }
        if((dir <= used) && (v[dir] > v[max])){
            max = dir;
        }
        if(pos != max){
            int aux;
            aux = v[pos];
            v[pos] = v[parent(pos)];
            v[parent(pos)] = aux;
            sift_down(max);
        }
    }