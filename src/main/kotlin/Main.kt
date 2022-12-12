import java.io.OutputStreamWriter
import java.util.*


class BNode(var data: Int) {
    var left: BNode? = null
    var right: BNode? = null

    fun toString(prefix: StringBuilder?, isTail: Boolean, sb: StringBuilder): StringBuilder {
        if (right != null) {
            right!!.toString(StringBuilder().append(prefix).append(if (isTail) "│   " else "    "), false, sb)
        }
        sb.append(prefix).append(if (isTail) "└── " else "┌── ").append(data.toString()).append("\n")
        if (left != null) {
            left!!.toString(StringBuilder().append(prefix).append(if (isTail) "    " else "│   "), true, sb)
        }
        return sb
    }

    override fun toString(): String {
        return this.toString(StringBuilder(), true, StringBuilder()).toString()
    }
}

class BST {
    lateinit var root: BNode
    fun isRootInitialized() = this::root.isInitialized

    fun add(value: Int): Int {
        val newNode: BNode
        var current: BNode?
        newNode = BNode(value)
        if (!isRootInitialized()) {
            root = newNode
            current = root
        } else {
            current = root
            while (current!!.left != null || current.right != null) {
                current = if (newNode.data < current.data) {
                    if (current.left != null) current.left else break
                } else {
                    if (current.right != null) current.right else break
                }
            }
            if (newNode.data < current.data) current.left = newNode else current.right = newNode
        }
        return value
    }

    fun inorder(root: BNode?) {
        if (root != null) {
            inorder(root.left)
            print("${root.data} ")
            inorder(root.right)
        }
    }

    fun isValueInTree(value: Int) = find(value).let {
        if (it)
            println("Число $value есть в дереве")
        else
            println("Числа $value в дереве нет")
        return@let it
    }

    fun find(value: Int): Boolean {
        if (!isRootInitialized())
            return false

        var current: BNode? = root

        while (current != null) {
            if (current.data == value)
                return true
            else if (current.data > value)
                current = current.left
            else
                current = current.right
        }

        return false
    }

    fun successor(value: Int) {
        if (!isRootInitialized())
            return

        var current: BNode? = root

        if (isValueInTree(value)) {
            var resultString = "Путь к вершине: "
            while (current!!.data != value) {
                resultString += "${current.data} -> "
                current = if (value < current.data && current.left != null) {
                    current.left
                } else if (value > current.data && current.right != null) {
                    current.right
                } else
                    current
            }
            resultString += current.data
            println(resultString)
        }
    }
}

fun main() {
    val valuesList = arrayListOf<Int>()
    val b = BST()
    var i = 25
    while (i-- > 0) {
        (10..99).random().let {
            if (b.find(it))
                i++
            else {
                b.add(it)
                valuesList.add(it)
            }
        }
    }
    println(b.root)
    println("Симметричный способ прохождения:")
    b.inorder(b.root)
    println("\n")
    b.isValueInTree(30)
    b.isValueInTree(90)
    b.isValueInTree(valuesList.random())
    b.isValueInTree(50)
    println()
    b.successor(100)
    println()
    b.successor(valuesList.random())
}