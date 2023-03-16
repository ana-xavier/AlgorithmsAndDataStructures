private void sift_up (int pos) {
        int ppai = parent(pos);
        while((pos > 0) && (v[ppai] < v[pos])){
            int aux = v[pos];
            v[pos] = v[ppai];
            v[ppai] = aux;
            
            pos = ppai;
            ppai = parent(pos);
       }
    }
