# 1. 导入 Flask 模块和 request 对象
from flask import Flask, request, jsonify

# 2. 创建 Flask 应用程序实例
app = Flask(__name__)

# 3. 全局变量，存储待办事项列表（初始为空）
todos = []

# 4. 路由 /todos：处理 GET 和 POST 请求
@app.route('/todos', methods=['GET', 'POST'])
def handle_todos():
    global todos  # 声明使用全局变量
    # 处理 GET 请求：返回所有待办事项
    if request.method == 'GET':
        return jsonify({"todos": todos})

    # 处理 POST 请求：添加新的待办事项
    if request.method == 'POST':
        # 获取请求中的 JSON 数据
        new_todo = request.get_json()
        # 校验数据是否包含 content 字段
        if not new_todo or 'content' not in new_todo:
            return jsonify({"error": "请传入待办事项内容 content"}), 400
        # 添加到列表
        todos.append(new_todo['content'])
        return jsonify({"message": "添加成功", "todos": todos}), 201

# 5. 路由 /todos/<int:index>：处理 GET 和 DELETE 请求
@app.route('/todos/<int:index>', methods=['GET', 'DELETE'])
def handle_single_todo(index):
    global todos
    # 校验索引是否合法
    if index < 0 or index >= len(todos):
        return jsonify({"error": "索引不存在"}), 404

    # 处理 GET 请求：返回指定索引的事项
    if request.method == 'GET':
        return jsonify({"todo": todos[index]})

    # 处理 DELETE 请求：删除指定索引的事项
    if request.method == 'DELETE':
        deleted_todo = todos.pop(index)
        return jsonify({"message": "删除成功", "deleted": deleted_todo, "todos": todos})

# 运行应用
if __name__ == '__main__':
    app.run(debug=True)