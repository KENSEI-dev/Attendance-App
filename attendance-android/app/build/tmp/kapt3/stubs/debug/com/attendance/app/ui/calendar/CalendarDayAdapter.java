package com.attendance.app.ui.calendar;

/**
 * Plain RecyclerView.Adapter, not ListAdapter/DiffUtil — a month is at most
 * 42 cells, and the whole grid is rebuilt as one unit whenever any input
 * flow changes (see CalendarViewModel.calendarDays), so there's no
 * meaningful partial-update case for DiffUtil to optimize here.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0016B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\u00062\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016J\u0014\u0010\u0014\u001a\u00020\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\tR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/attendance/app/ui/calendar/CalendarDayAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/attendance/app/ui/calendar/CalendarDayAdapter$ViewHolder;", "onDayClick", "Lkotlin/Function1;", "Ljava/time/LocalDate;", "", "(Lkotlin/jvm/functions/Function1;)V", "days", "", "Lcom/attendance/app/ui/calendar/CalendarDayUi;", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "newDays", "ViewHolder", "app_debug"})
public final class CalendarDayAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.attendance.app.ui.calendar.CalendarDayAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.time.LocalDate, kotlin.Unit> onDayClick = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.attendance.app.ui.calendar.CalendarDayUi> days;
    
    public CalendarDayAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDayClick) {
        super();
    }
    
    public final void submitList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.ui.calendar.CalendarDayUi> newDays) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.attendance.app.ui.calendar.CalendarDayAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.attendance.app.ui.calendar.CalendarDayAdapter.ViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/attendance/app/ui/calendar/CalendarDayAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/attendance/app/databinding/ItemCalendarDayBinding;", "(Lcom/attendance/app/ui/calendar/CalendarDayAdapter;Lcom/attendance/app/databinding/ItemCalendarDayBinding;)V", "getBinding", "()Lcom/attendance/app/databinding/ItemCalendarDayBinding;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.attendance.app.databinding.ItemCalendarDayBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.attendance.app.databinding.ItemCalendarDayBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.databinding.ItemCalendarDayBinding getBinding() {
            return null;
        }
    }
}