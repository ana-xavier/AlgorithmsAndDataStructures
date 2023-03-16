    private void sift_down(int pos){
        int max = pos;
        int esq = left(pos);
        int dir = right(pos);

        if((esq < used) && (v[esq] > v[max])) max = esq;
        if((dir < used) && (v[dir] > v[max])) max = dir;

        if(pos != max){
            int aux = v[pos];
            v[pos] = v[max];
            v[max] = aux;
            sift_down(max); //recursao
        }
    }
