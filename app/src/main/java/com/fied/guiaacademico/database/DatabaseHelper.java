package com.fied.guiaacademico.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fied.guiaacademico.models.Avaliacao;
import com.fied.guiaacademico.models.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "guia_academico.db";
    private static final int DATABASE_VERSION = 9;

    // Tabela Disciplinas
    private static final String TABLE_DISCIPLINAS = "disciplinas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODIGO = "codigo";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_CREDITOS = "creditos";
    private static final String COLUMN_CARGA_HORARIA = "carga_horaria";
    private static final String COLUMN_PERIODO = "periodo";
    private static final String COLUMN_PRE_REQUISITOS = "pre_requisitos";
    private static final String COLUMN_EMENTA = "ementa";
    private static final String COLUMN_BIBLIOGRAFIA = "bibliografia";

    // Tabela Avaliações
    private static final String TABLE_AVALIACOES = "avaliacoes";
    private static final String COLUMN_DISCIPLINA_ID = "disciplina_id";
    private static final String COLUMN_NOTA = "nota";
    private static final String COLUMN_COMENTARIO = "comentario";
    private static final String COLUMN_DATA = "data";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DISCIPLINAS_TABLE = "CREATE TABLE " + TABLE_DISCIPLINAS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CODIGO + " TEXT,"
                + COLUMN_NOME + " TEXT,"
                + COLUMN_CREDITOS + " INTEGER,"
                + COLUMN_CARGA_HORARIA + " INTEGER,"
                + COLUMN_PERIODO + " INTEGER,"
                + COLUMN_PRE_REQUISITOS + " TEXT,"
                + COLUMN_EMENTA + " TEXT,"
                + COLUMN_BIBLIOGRAFIA + " TEXT"
                + ")";
        db.execSQL(CREATE_DISCIPLINAS_TABLE);

        String CREATE_AVALIACOES_TABLE = "CREATE TABLE " + TABLE_AVALIACOES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DISCIPLINA_ID + " INTEGER,"
                + COLUMN_NOTA + " REAL,"
                + COLUMN_COMENTARIO + " TEXT,"
                + COLUMN_DATA + " INTEGER"
                + ")";
        db.execSQL(CREATE_AVALIACOES_TABLE);

        inserirDadosIniciais(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(disciplinas)", null);
        boolean hasPeriodoColumn = false;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String columnName = cursor.getString(cursor.getColumnIndex("name"));
                if ("periodo".equals(columnName)) {
                    hasPeriodoColumn = true;
                    break;
                }
            }
            cursor.close();
        }

        if (!hasPeriodoColumn) {
            db.execSQL("ALTER TABLE " + TABLE_DISCIPLINAS + " ADD COLUMN " + COLUMN_PERIODO + " INTEGER DEFAULT 1");
        }

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AVALIACOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINAS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void inserirDadosIniciais(SQLiteDatabase db) {
        // 1º Período
        inserirDisciplina(db, "SINF043", "Fundamentos de Sistemas de Informação", 3, 45, 1, null, 
            "Introdução aos conceitos fundamentais de Sistemas de Informação. Tipos de sistemas de informação. Papel estratégico dos sistemas de informação nas organizações.",
            "LAUDON, K. C.; LAUDON, J. P. Sistemas de Informação Gerenciais. 9. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF044", "Lógica Matemática Computacional", 3, 45, 1, null,
            "Lógica proposicional. Lógica de predicados. Métodos de prova. Indução matemática. Recursão.",
            "SOUZA, J. N. Lógica para Ciência da Computação. 2. ed. Rio de Janeiro: Campus, 2008.");

        inserirDisciplina(db, "SINF082", "Filosofia", 3, 45, 1, null,
            "Introdução à filosofia. História da filosofia. Principais correntes filosóficas.",
            "CHAUÍ, M. Convite à Filosofia. 13. ed. São Paulo: Ática, 2003.");

        inserirDisciplina(db, "SINF084", "Metodologia da Pesquisa Científica", 4, 60, 1, "CSAP040",
            "Métodos e técnicas de pesquisa. Elaboração de trabalhos acadêmicos.",
            "LAKATOS, E. M.; MARCONI, M. A. Metodologia do Trabalho Científico. 7. ed. São Paulo: Atlas, 2007.");

        inserirDisciplina(db, "SINF108", "Introdução à Administração", 4, 60, 1, null,
            "Conceitos básicos de administração. Funções administrativas. Processos organizacionais.",
            "CHIAVENATO, I. Introdução à Teoria Geral da Administração. 8. ed. Rio de Janeiro: Campus, 2011.");

        inserirDisciplina(db, "SINF150", "Matemática Básica", 4, 75, 1, null,
            "Conjuntos numéricos. Funções. Equações e inequações. Matrizes e determinantes.",
            "IEZZI, G. et al. Matemática Elementar. 6. ed. São Paulo: Atual, 2005.");

        inserirDisciplina(db, "SINF151", "Cálculo I", 4, 75, 1, "SINF150",
            "Limites e continuidade. Derivadas. Aplicações de derivadas. Integrais.",
            "STEWART, J. Cálculo. 7. ed. São Paulo: Cengage Learning, 2013.");

        inserirDisciplina(db, "CSAP040", "Leitura, Interpretação e Produção Textual", 3, 45, 1, null,
            "Técnicas de leitura e interpretação. Produção de textos acadêmicos.",
            "FIORIN, J. L.; SAVIOLI, F. P. Para Entender o Texto. 5. ed. São Paulo: Ática, 2007.");

        inserirDisciplina(db, "COMMUENG01", "Communicative English I", 2, 30, 1, null,
            "Inglês instrumental. Compreensão e produção de textos em inglês.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        // 2º Período
        inserirDisciplina(db, "SINF085", "Organização e Arquitetura de Computadores", 4, 60, 2, "SINF044",
            "Arquitetura de computadores. Componentes básicos. Memória. Processador. Periféricos.",
            "STALLINGS, W. Arquitetura e Organização de Computadores. 8. ed. São Paulo: Pearson, 2010.");

        inserirDisciplina(db, "SINF086", "Técnicas de Programação I", 5, 75, 2, "SINF044",
            "Introdução à programação. Algoritmos. Estruturas de controle. Funções. Arrays. Ponteiros. Estruturas.",
            "DEITEL, H. M.; DEITEL, P. J. C: Como Programar. 6. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF087", "Teoria Geral dos Sistemas", 3, 45, 2, "SINF043",
            "Conceitos de sistemas. Tipos de sistemas. Análise sistêmica.",
            "BERTALANFFY, L. V. Teoria Geral dos Sistemas. Petrópolis: Vozes, 2008.");

        inserirDisciplina(db, "SINF088", "Banco de Dados I", 5, 75, 2, "SINF086",
            "Conceitos de banco de dados. Modelos de dados. Modelo relacional. Linguagem SQL. Projeto de banco de dados.",
            "ELMASRI, R.; NAVATHE, S. B. Sistemas de Banco de Dados. 6. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF152", "Empreendedorismo e Inovação", 3, 45, 2, "SINF108",
            "Conceitos de empreendedorismo. Inovação. Plano de negócios.",
            "DORNELAS, J. C. A. Empreendedorismo na Prática. Rio de Janeiro: Elsevier, 2008.");

        inserirDisciplina(db, "COMMUENG02", "Communicative English II", 2, 30, 2, "COMMUENG01",
            "Inglês instrumental avançado. Compreensão e produção de textos técnicos em inglês.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "UNCE01", "Unidade Curricular de Extensão I", 3, 45, 2, "SINF084",
            "Projetos de extensão. Ações sociais. Integração com a comunidade.",
            "Material didático específico do curso.");

        // 3º Período
        inserirDisciplina(db, "SINF112", "Gestão de Sistemas de Informação", 4, 75, 3, "SINF043",
            "Gestão de sistemas de informação. Planejamento estratégico de TI. Governança de TI.",
            "LAUDON, K. C.; LAUDON, J. P. Sistemas de Informação Gerenciais. 9. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF113", "Técnicas de Programação II", 4, 75, 3, "SINF086",
            "Programação avançada. Estruturas de dados. Algoritmos complexos.",
            "DEITEL, H. M.; DEITEL, P. J. C: Como Programar. 6. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF153", "Cálculo II", 4, 45, 3, "SINF151",
            "Integrais múltiplas. Séries. Equações diferenciais.",
            "STEWART, J. Cálculo. 7. ed. São Paulo: Cengage Learning, 2013.");

        inserirDisciplina(db, "SINF154", "Redes de Computadores", 4, 75, 3, "SINF085",
            "Fundamentos de redes. Protocolos. Arquitetura de redes.",
            "TANENBAUM, A. S. Redes de Computadores. 5. ed. Rio de Janeiro: Campus, 2011.");

        inserirDisciplina(db, "SINF155", "Banco de Dados II", 4, 75, 3, "SINF088",
            "Banco de dados avançado. Otimização. Data warehousing.",
            "ELMASRI, R.; NAVATHE, S. B. Sistemas de Banco de Dados. 6. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "COMMUENG03", "Communicative English III", 2, 30, 3, "COMMUENG02",
            "Inglês técnico avançado. Comunicação profissional.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "UNCE02", "Unidade Curricular de Extensão II", 3, 45, 3, "UNCE01",
            "Projetos de extensão avançados. Desenvolvimento comunitário.",
            "Material didático específico do curso.");

        // 4º Período
        inserirDisciplina(db, "SINF091", "Programação Orientada a Objetos", 5, 75, 4, "SINF113",
            "Conceitos de POO. Classes e objetos. Herança. Polimorfismo.",
            "DEITEL, H. M.; DEITEL, P. J. Java: Como Programar. 8. ed. São Paulo: Pearson, 2010.");

        inserirDisciplina(db, "SINF093", "Matemática Discreta", 3, 45, 4, "SINF044",
            "Teoria dos conjuntos. Lógica matemática. Álgebra booleana.",
            "ROSEN, K. H. Matemática Discreta e suas Aplicações. 6. ed. São Paulo: McGraw-Hill, 2009.");

        inserirDisciplina(db, "SINF094", "Sistemas Operacionais", 3, 45, 4, "SINF085",
            "Conceitos de sistemas operacionais. Processos. Memória. Arquivos.",
            "TANENBAUM, A. S. Sistemas Operacionais Modernos. 3. ed. São Paulo: Pearson, 2010.");

        inserirDisciplina(db, "SINF114", "Tópicos em Direito Público e Privado", 2, 45, 4, null,
            "Fundamentos do direito. Direito digital. Propriedade intelectual.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "CSAP027", "Estatística e Probabilidade", 3, 45, 4, "SINF150",
            "Estatística descritiva. Probabilidade. Inferência estatística.",
            "MORETTIN, P. A.; BUSSAB, W. O. Estatística Básica. 6. ed. São Paulo: Saraiva, 2010.");

        inserirDisciplina(db, "COMMUENG04", "Communicative English IV", 2, 30, 4, "COMMUENG03",
            "Inglês para negócios. Comunicação corporativa.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        // 5º Período
        inserirDisciplina(db, "SINF095", "Álgebra Linear", 3, 45, 5, "SINF153",
            "Matrizes. Sistemas lineares. Espaços vetoriais.",
            "BOLDRINI, J. L. et al. Álgebra Linear. 3. ed. São Paulo: Harbra, 1986.");

        inserirDisciplina(db, "SINF096", "Engenharia de Software", 5, 75, 5, "SINF091",
            "Processos de software. Metodologias ágeis. Qualidade de software.",
            "SOMMERVILLE, I. Engenharia de Software. 9. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF098", "Programação Web", 5, 75, 5, "SINF091",
            "Desenvolvimento web. HTML5. CSS3. JavaScript. Frameworks web.",
            "FLANAGAN, D. JavaScript: O Guia Definitivo. 6. ed. São Paulo: Bookman, 2012.");

        inserirDisciplina(db, "SINF156", "Auditoria e Segurança em Sistemas de Informação", 4, 75, 5, "SINF112",
            "Segurança da informação. Auditoria de sistemas. Políticas de segurança.",
            "STALLINGS, W. Criptografia e Segurança de Redes. 4. ed. São Paulo: Pearson, 2008.");

        inserirDisciplina(db, "COMMUENG05", "Communicative English V", 2, 30, 5, "COMMUENG04",
            "Inglês para tecnologia. Comunicação técnica.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "UNCE03", "Unidade Curricular de Extensão III", 3, 45, 5, "UNCE02",
            "Projetos de extensão especializados. Inovação social.",
            "Material didático específico do curso.");

        // 6º Período
        inserirDisciplina(db, "SINF101", "Governança de Tecnologia da Informação", 3, 45, 6, "SINF112",
            "Governança de TI. Frameworks. Melhores práticas.",
            "WEILL, P.; ROSS, J. W. Governança de TI. São Paulo: M. Books, 2006.");

        inserirDisciplina(db, "SINF116", "Comportamento Organizacional", 4, 75, 6, "SINF108",
            "Comportamento humano nas organizações. Liderança. Cultura organizacional.",
            "ROBBINS, S. P. Comportamento Organizacional. 14. ed. São Paulo: Pearson, 2010.");

        inserirDisciplina(db, "SINF117", "Análise e Projetos de Sistemas", 4, 75, 6, "SINF096",
            "Análise de requisitos. Projeto de sistemas. UML.",
            "SOMMERVILLE, I. Engenharia de Software. 9. ed. São Paulo: Pearson, 2011.");

        inserirDisciplina(db, "SINF118", "Gestão de Projetos", 3, 45, 6, "SINF096",
            "Metodologias de gestão de projetos. PMBOK. Scrum.",
            "PMI. Guia PMBOK. 5. ed. Pennsylvania: Project Management Institute, 2013.");

        inserirDisciplina(db, "SINF158", "Unidade Curricular de Extensão V", 3, 30, 6, "UNCE03",
            "Projetos de extensão integrados. Desenvolvimento sustentável.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "SINF157", "Responsabilidade Socioambiental", 2, 45, 6, null,
            "Desenvolvimento sustentável. Responsabilidade social. Ética empresarial.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "COMMUENG06", "Communicative English VI", 2, 30, 6, "COMMUENG05",
            "Inglês para gestão. Liderança e comunicação.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "UNCE04", "Unidade Curricular de Extensão IV", 3, 45, 6, "UNCE03",
            "Projetos de extensão avançados. Impacto social.",
            "Material didático específico do curso.");

        // 7º Período
        inserirDisciplina(db, "SINF103", "Inteligência Artificial", 5, 75, 7, "SINF113",
            "Fundamentos de IA. Aprendizado de máquina. Redes neurais.",
            "RUSSELL, S.; NORVIG, P. Inteligência Artificial. 3. ed. Rio de Janeiro: Campus, 2013.");

        inserirDisciplina(db, "SINF105", "Tópicos em Desenvolvimento Mobile", 5, 75, 7, "SINF098",
            "Desenvolvimento de aplicativos móveis. Android. iOS.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "SINF159", "Projeto Supervisionado ou de Graduação I", 4, 150, 7, "SINF117",
            "Desenvolvimento de projeto de conclusão de curso.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "COMMUENG07", "Communicative English VII", 2, 30, 7, "COMMUENG06",
            "Inglês para pesquisa. Comunicação acadêmica.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "UNCE006", "Unidade Curricular de Extensão VI", 3, 45, 7, "UNCE04",
            "Projetos de extensão finais. Impacto social.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "OPTATIVA_I", "Disciplina Optativa I", 3, 45, 7, null,
            "Disciplina optativa a ser escolhida pelo aluno.",
            "Material didático específico do curso.");

        // 8º Período
        inserirDisciplina(db, "SINF106", "Gestão de Qualidade de Software", 4, 60, 8, "SINF096",
            "Qualidade de software. Testes. Certificação.",
            "PRESSMAN, R. S. Engenharia de Software. 7. ed. São Paulo: McGraw-Hill, 2011.");

        inserirDisciplina(db, "SINF120", "Cloud Computing", 3, 45, 8, "SINF154",
            "Computação em nuvem. Virtualização. Serviços cloud.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "SINF160", "Projeto Supervisionado ou de Graduação II", 4, 150, 8, "SINF159",
            "Conclusão e apresentação do projeto de graduação.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "SINF161", "Unidade Curricular de Extensão VII", 2, 45, 8, "UNCE006",
            "Projetos de extensão finais. Legado social.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "COMMUENG08", "Communicative English VIII", 2, 30, 8, "COMMUENG07",
            "Inglês para apresentações. Comunicação profissional.",
            "MURPHY, R. English Grammar in Use. 4. ed. Cambridge: Cambridge University Press, 2012.");

        inserirDisciplina(db, "OPTATIVA_II", "Disciplina Optativa II", 3, 45, 8, null,
            "Disciplina optativa a ser escolhida pelo aluno.",
            "Material didático específico do curso.");

        inserirDisciplina(db, "OPTATIVA_III", "Disciplina Optativa III", 3, 45, 8, null,
            "Disciplina optativa a ser escolhida pelo aluno.",
            "Material didático específico do curso.");
    }

    private void inserirDisciplina(SQLiteDatabase db, String codigo, String nome, int creditos, int cargaHoraria,
                                 int periodo, String preRequisitos, String ementa, String bibliografia) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODIGO, codigo);
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_CREDITOS, creditos);
        values.put(COLUMN_CARGA_HORARIA, cargaHoraria);
        values.put(COLUMN_PERIODO, periodo);
        values.put(COLUMN_PRE_REQUISITOS, preRequisitos);
        values.put(COLUMN_EMENTA, ementa);
        values.put(COLUMN_BIBLIOGRAFIA, bibliografia);
        db.insert(TABLE_DISCIPLINAS, null, values);
    }

    @SuppressLint("Range")
    public List<Disciplina> getAllDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DISCIPLINAS + " ORDER BY " + COLUMN_PERIODO + ", " + COLUMN_NOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)));
                disciplina.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
                disciplina.setCreditos(cursor.getInt(cursor.getColumnIndex(COLUMN_CREDITOS)));
                disciplina.setCargaHoraria(cursor.getInt(cursor.getColumnIndex(COLUMN_CARGA_HORARIA)));
                disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(COLUMN_PERIODO)));
                disciplina.setPreRequisitos(cursor.getString(cursor.getColumnIndex(COLUMN_PRE_REQUISITOS)));
                disciplina.setEmenta(cursor.getString(cursor.getColumnIndex(COLUMN_EMENTA)));
                disciplina.setBibliografia(cursor.getString(cursor.getColumnIndex(COLUMN_BIBLIOGRAFIA)));
                disciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return disciplinas;
    }

    @SuppressLint("Range")
    public List<Disciplina> getDisciplinasByPeriodo(int periodo) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DISCIPLINAS + " WHERE " + COLUMN_PERIODO + " = ? ORDER BY " + COLUMN_NOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(periodo)});

        if (cursor.moveToFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)));
                disciplina.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
                disciplina.setCreditos(cursor.getInt(cursor.getColumnIndex(COLUMN_CREDITOS)));
                disciplina.setCargaHoraria(cursor.getInt(cursor.getColumnIndex(COLUMN_CARGA_HORARIA)));
                disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(COLUMN_PERIODO)));
                disciplina.setPreRequisitos(cursor.getString(cursor.getColumnIndex(COLUMN_PRE_REQUISITOS)));
                disciplina.setEmenta(cursor.getString(cursor.getColumnIndex(COLUMN_EMENTA)));
                disciplina.setBibliografia(cursor.getString(cursor.getColumnIndex(COLUMN_BIBLIOGRAFIA)));
                disciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return disciplinas;
    }

    @SuppressLint("Range")
    public Disciplina getDisciplinaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISCIPLINAS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        Disciplina disciplina = null;
        if (cursor != null && cursor.moveToFirst()) {
            disciplina = new Disciplina();
            disciplina.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
            disciplina.setCreditos(cursor.getInt(cursor.getColumnIndex(COLUMN_CREDITOS)));
            disciplina.setCargaHoraria(cursor.getInt(cursor.getColumnIndex(COLUMN_CARGA_HORARIA)));
            disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(COLUMN_PERIODO)));
            disciplina.setPreRequisitos(cursor.getString(cursor.getColumnIndex(COLUMN_PRE_REQUISITOS)));
            disciplina.setEmenta(cursor.getString(cursor.getColumnIndex(COLUMN_EMENTA)));
            disciplina.setBibliografia(cursor.getString(cursor.getColumnIndex(COLUMN_BIBLIOGRAFIA)));
            cursor.close();
        }

        return disciplina;
    }

    public long addAvaliacao(Avaliacao avaliacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISCIPLINA_ID, avaliacao.getDisciplinaId());
        values.put(COLUMN_NOTA, avaliacao.getNota());
        values.put(COLUMN_COMENTARIO, avaliacao.getComentario());
        values.put(COLUMN_DATA, avaliacao.getData());
        return db.insert(TABLE_AVALIACOES, null, values);
    }

    @SuppressLint("Range")
    public List<Avaliacao> getAvaliacoesByDisciplina(int disciplinaId) {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_AVALIACOES + " WHERE " + COLUMN_DISCIPLINA_ID + " = ? ORDER BY " + COLUMN_DATA + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(disciplinaId)});

        if (cursor.moveToFirst()) {
            do {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                avaliacao.setDisciplinaId(cursor.getInt(cursor.getColumnIndex(COLUMN_DISCIPLINA_ID)));
                avaliacao.setNota(cursor.getFloat(cursor.getColumnIndex(COLUMN_NOTA)));
                avaliacao.setComentario(cursor.getString(cursor.getColumnIndex(COLUMN_COMENTARIO)));
                avaliacao.setData(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA)));
                avaliacoes.add(avaliacao);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return avaliacoes;
    }

    @SuppressLint("Range")
    public List<Avaliacao> getAllAvaliacoes() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_AVALIACOES + " ORDER BY " + COLUMN_DATA + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                avaliacao.setDisciplinaId(cursor.getInt(cursor.getColumnIndex(COLUMN_DISCIPLINA_ID)));
                avaliacao.setNota(cursor.getFloat(cursor.getColumnIndex(COLUMN_NOTA)));
                avaliacao.setComentario(cursor.getString(cursor.getColumnIndex(COLUMN_COMENTARIO)));
                avaliacao.setData(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA)));
                avaliacoes.add(avaliacao);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return avaliacoes;
    }

    @SuppressLint("Range")
    public Disciplina getDisciplinaByCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISCIPLINAS, null, COLUMN_CODIGO + " = ?",
                new String[]{codigo.trim()}, null, null, null);
        
        Disciplina disciplina = null;
        if (cursor != null && cursor.moveToFirst()) {
            disciplina = new Disciplina();
            disciplina.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
            disciplina.setCreditos(cursor.getInt(cursor.getColumnIndex(COLUMN_CREDITOS)));
            disciplina.setCargaHoraria(cursor.getInt(cursor.getColumnIndex(COLUMN_CARGA_HORARIA)));
            disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(COLUMN_PERIODO)));
            disciplina.setPreRequisitos(cursor.getString(cursor.getColumnIndex(COLUMN_PRE_REQUISITOS)));
            disciplina.setEmenta(cursor.getString(cursor.getColumnIndex(COLUMN_EMENTA)));
            disciplina.setBibliografia(cursor.getString(cursor.getColumnIndex(COLUMN_BIBLIOGRAFIA)));
            cursor.close();
        }
        return disciplina;
    }
}