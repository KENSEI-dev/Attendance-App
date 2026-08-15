package com.attendance.app.ui.subjects;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/attendance/app/ui/subjects/SubjectsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/attendance/app/repository/AttendanceRepository;", "(Lcom/attendance/app/repository/AttendanceRepository;)V", "subjects", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/attendance/app/data/SubjectEntity;", "getSubjects", "()Lkotlinx/coroutines/flow/StateFlow;", "addSubject", "", "name", "", "code", "deleteSubject", "subject", "app_debug"})
public final class SubjectsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.repository.AttendanceRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.data.SubjectEntity>> subjects = null;
    
    public SubjectsViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.repository.AttendanceRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.data.SubjectEntity>> getSubjects() {
        return null;
    }
    
    public final void addSubject(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String code) {
    }
    
    public final void deleteSubject(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.SubjectEntity subject) {
    }
}