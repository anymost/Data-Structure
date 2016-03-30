
//先序遍历迭代版
void travPre(Binnode x) {
	Stack<Binnode> stack;
	while (true) {
		while (x) {
			System.out.println(x);
			stack.push(x.rchild);
			x = x.lchild;
		}
		if (stack.empty())break;
		x = stack.pop();
	}
}

//中序遍历迭代版
void travIn(Binnode x) {
	Stack<Binnode> stack;
	while (true) {
		while (x) {
			stack.push(x);
			x = x.lchild;
		}
		if (stack.empty())break;
		x = stack.pop();
		System.out.println(x);
		x = x.rchild;
	}
}

//后序遍历迭代版
void travPost(Binnode x) {
	Stack<Binnode> stack;
	if (x)stack.push(x);
	while (!s.empty()) {
		if (s.top() != parent) {
			while (x = stack.top()) {
				if (x.hasRchild) {
					s.push(x.rchild);
					s.push(x.lchild);
				} else
					s.push(x.rchild);
				s.pop();
			}
		}
		x = stack.pop();
		System.out.println(x);
	}
}

//层次遍历
void travLevel(Binnode x) {
	Queue<Binnode> queue;
	queue.enque(Binnode);
	while (!x.empty()) {
		x = s.deque();
		System.out.println(x);
		queue.enque(x.lchild);
		queue.enque(x.rchild);
	}
}

//中序遍历中的后继节点
Binnode succ() {
	binnode s = this;
	if (s.hasRchild) {
		s = rchild;
		while (s.hasLchild) {
			s = s.lchild;
		}
		return s;
	} else {
		while (isRchild(s))s = s.parent;
		s = s.parent;
		return s;
	}
}



//深度优先遍历
void DFS(int v) {
	int[] visited = new int[size];
	for (int i = 0; i < size; i++)
		visited[i] = 0;
	Stack<int> stack = new Stack<>();
	stack.push(v);
	int w;
	while (!stack.empty()) {
		w = stack.pop();
		System.out.println(w);
		visited[w] = 1;
		int k = w.getFirstNeighbor();
		while (k != -1) {
			if (visited[k] == 0) {
				stack.push(k);
			}
			k = w.getNextNeighbor(k);
		}
	}
}
//广度优先遍历
void BFS(int v) {
	int[] visited = new int[size];
	for (int i = 0; i < size; i++)
		visited[i] = 0;
	Queue<int> queue = new Queue<>();
	System.out.println(v);
	visited[v] = 1;
	queue.enqueue(v);
	int w = 0;
	while (!queue.empty()) {
		w = queue.deque();
		k = w.getFirstNeighbor();
		while (k != -1) {
			if (visited[k] == 0) {
				System.out.println(k);
				visited[k] = 1;
				queue.enqueue(k);
			}
			k = w.getNextNeightbor(k);
		}
	}
}
void topoOrder() {
	int[] count = new int[size];
	for (int i = 0; i < size; i++)
		count = 0;
	for (int i = 0; i < size; i++) {
		Edge e = head[i].adjacent();
		while (e != null) {
			int v = e.vertex;
			count[v]++;
			e = e.next();
		}
	}
	int top = -1;
	for (int i = 0; i < size; i++) {
		if (count[i] == 0) {
			count[i] = top;
			top = i;
		}
	}
	for (int i = 0; i < size; i++) {
		if (top == -1) {
			System.out.println("cycle");
		} else {
			int p = top;
			top = count[top];
			Edge p = head[p].adjacent();
			while (p != null) {
				int k = p.vertext();
				count[k]--;
				if (count[k] == 0) {
					count[k] = top;
					top = k;
				}
				p = p.next();
			}
		}
	}

}
//Dijkstra算法求权值为1的最短单源路径 O(n^2)
/*
①设S为初始顶点, Ds=0且  i≠ S，有Di =＋∞
②在未访问顶点中选择Dv最小的顶点v，访问v，令 S[v]=1。
③依次考察v的邻接顶点w，若
    Dv+weight(<v,w>)< Dw ，
  则改变Dw的值，使Dw = Dv + weight(<v,w>) 。
④重复② ③ ，直至所有顶点被访问。

*/
void shortestPath(int v) {
	int u, k;
	int max = 10000;
	Edge p;
	int n = size;
	int[]  path = new int[size];
	int[] dist = new int[size];
	int[] s = new int[size];
	for (int i = 0; i < n; i++) {
		path[i] = -1;
		dist[i] = max;
		s[i] = 0;
	}
	dist[v] = 0;
	s[v] = 1;
	p = head[v].adjacent;
	u = v;
	for (int j = 0; j < n; j++) {
		//对dist最小的那个顶点所邻接的点进行修改dist
		while (p != null) {
			k = p.vertex;
			if (s[k] != 1 && (dist[u] + p.cost) < dist[k]) {
				dist[k] = dist[u] + p.cost;
				path[k] = u;
			}
			p = p.next;
		}
		int ldist = max;
		//寻找v中dist值最小的点
		for (i = 0; i < n; i++)
			if (dist[i] > 0 && dist[i] <ldist&& s[i] == 0) {
				ldist = dist[i];
				u = i;
			}
		s[u] = 1;
		p = head[u].adjacent;
	}
	for (i = 0; i < n; i++)System.out.println(path[i]);
	for (i = 0; i < n; i++)System.out.println(dist[i]);
}

/*

Prim算法 求最小生成树 O(n^2)
设G=(V,E,C)为连通网，TE是G的最小生成树MST的边的集合，U为MST顶点集。
   ① U={uo}(uo∈V), TE= Φ ；
   ② 找到满足
weight(u,v)＝min{weight(u1,v1)|u1∈U, v1∈V-U},
 的边，把它并入TE，同时v并入U；
   ③ 反复执行② ,直至 V=U , 算法结束。 

*/

/*
Kruskal算法 求最小支撑树 O(elge)
设连通网G=(V,E,C)，T为N的最小生成树。初始时T={V,Φ}，即T中没有边，只有n个顶点，也就是n个连通分量。
①在E中选择权值最小的边，并将此边从E中删除。
②如果此边的两个顶点在T的不同的连通分量中，则将此边加入到T中，从而导致T中减少一个连通分量;
如果此边的两个顶点在同一连通分量中，则重复执行① ② ，直至T中仅剩一个连通分量时，终止操作。
*/
