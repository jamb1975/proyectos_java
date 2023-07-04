package sihic;

import sihic.control.Popover;
import sihic.control.PopoverTreeList;
import sihic.search.DocumentType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modelo.ActividadesEconomicas;
import modelo.AgnCitas;
import modelo.AgnConsultorios;
import modelo.AgnMedicos;
import modelo.ComprobanteCausacionEgreso;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.FacturaProveedores;
import modelo.GenConvenios;
import modelo.GenEapb;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenHorasCita;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenNivelesUsuarios;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposAfiliacion;
import modelo.GenTiposDocumentos;
import modelo.GenTiposUsuarios;
import modelo.GenZonaResidencia;
import modelo.HclCie10;
import modelo.HclCups;
import modelo.Kardex;
import modelo.Producto;
import modelo.Proveedores;
import modelo.RetencionFuente;
import modelo.Sucursales;

/**
 * Popover page that displays a list of search results.
 */
public class SearchResultPopoverList extends PopoverTreeList<Object> implements Popover.Page {

    private Popover popover;
    private PageBrowser pageBrowser;
    private Rectangle leftLine = new Rectangle(0, 0, 1, 1);
    private IconPane iconPane = new IconPane();
    private final Pane backgroundRectangle = new Pane();

    public SearchResultPopoverList(PageBrowser pageBrowser) {
        this.pageBrowser = pageBrowser;
        leftLine.setFill(Color.web("#dfdfdf"));
        iconPane.setManaged(false);
        setFocusTraversable(false);
        backgroundRectangle.setId("PopoverBackground");
        setPlaceholder(backgroundRectangle);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        //  if (leftLine.getParent() != this) getChildren().add(leftLine);
        //  if (iconPane.getParent() != this) getChildren().add(iconPane);
        leftLine.setLayoutX(40);
        leftLine.setLayoutY(0);
        leftLine.setHeight(getHeight());
        iconPane.setLayoutX(0);
        iconPane.setLayoutY(0);
        iconPane.resize(getWidth(), getHeight());
        backgroundRectangle.resize(getWidth(), getHeight());
    }

    @Override
    public void itemClicked(Object item) {

        if (Puc.class == item.getClass()) {
            SihicApp.conPuc = ((Puc) item);
        } else {
            if (ComprobanteCausacionEgreso.class == item.getClass()) {
                SihicApp.s_conComprobanteGastos = (ComprobanteCausacionEgreso) item;
            } else {
                if (GenEapb.class == item.getClass()) {
                    SihicApp.s_genEapb = (GenEapb) item;
                } else {
                    if (HclCups.class == item.getClass()) {
                        SihicApp.s_hclCups = (HclCups) item;
                    } else {
                        if (Producto.class == item.getClass()) {
                            SihicApp.s_producto = (Producto) item;
                        } else {
                            if (Kardex.class == item.getClass()) {
                                SihicApp.s_kardex = (Kardex) item;
                            } else {
                                if (GenMunicipios.class == item.getClass()) {
                                    SihicApp.s_genMunicipios = (GenMunicipios) item;
                                } else {
                                    if (GenProfesiones.class == item.getClass()) {
                                        SihicApp.s_GenProfesiones = (GenProfesiones) item;
                                    } else {
                                        if (Factura.class == item.getClass()) {
                                            SihicApp.s_factura = (Factura) item;
                                        } else {
                                            if (FacturaItem.class == item.getClass()) {
                                                SihicApp.s_facturaitem = (FacturaItem) item;
                                            } else {
                                                if (HclCie10.class == item.getClass()) {
                                                    SihicApp.hclCie10 = (HclCie10) item;
                                                } else {
                                                    if (ActividadesEconomicas.class == item.getClass()) {
                                                        SihicApp.s_ActividadesEconomicas = (ActividadesEconomicas) item;
                                                    } else {
                                                        if (FacturaProveedores.class == item.getClass()) {
                                                            SihicApp.s_FacturaProveedores = (FacturaProveedores) item;
                                                        }
                                                        else
                                                        {
                                                            if (GenPersonas.class == item.getClass()) {
                                                            SihicApp.genPersonas = (GenPersonas) item;
                                                        }
                                                        else
                                                            {
                                                                 if (GenTiposDocumentos.class == item.getClass()) {
                                                                   SihicApp.genTiposDocumentos = (GenTiposDocumentos) item;
                                                        }
                                                         else
                                                                 {
                                                                      if (GenSexo.class == item.getClass()) {
                                                                       SihicApp.genSexo = (GenSexo) item;
                                                        }
                                                                      else
                                                                      {
                                                                          if (GenEtnias.class == item.getClass())
                                                                          {
                                                                                SihicApp.genEtnias = (GenEtnias) item;
                                                                          }
                                                                          else
                                                                          {
                                                                              if (GenNivelesEducativos.class == item.getClass())
                                                                             {
                                                                                SihicApp.genNivelesEducativos = (GenNivelesEducativos) item;
                                                                             }
                                                                              else
                                                                              {
                                                                                  if (GenZonaResidencia.class == item.getClass())
                                                                                 {
                                                                                   SihicApp.genZonaResidencia = (GenZonaResidencia) item;
                                                                                 }
                                                                                  else
                                                                                  {
                                                                                    if (GenEstadosCiviles.class == item.getClass())
                                                                                    {
                                                                                       SihicApp.genEstadosCiviles = (GenEstadosCiviles) item;
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        if (GenTiposAfiliacion.class == item.getClass())
                                                                                       {
                                                                                          SihicApp.genTiposAfiliacion= (GenTiposAfiliacion) item;
                                                                                       }
                                                                                        else
                                                                                        {
                                                                                             if (GenTiposUsuarios.class == item.getClass())
                                                                                             {
                                                                                               SihicApp.genTiposUsuarios= (GenTiposUsuarios) item;
                                                                                             }
                                                                                             else
                                                                                             {
                                                                                               if (GenNivelesUsuarios.class == item.getClass())
                                                                                             {
                                                                                               SihicApp.genNivelesUsuarios= (GenNivelesUsuarios) item;
                                                                                             } 
                                                                                               else
                                                                                               {
                                                                                                 if (GenConvenios.class == item.getClass())
                                                                                                 {
                                                                                                    SihicApp.genConvenios= (GenConvenios) item;
                                                                                                 }
                                                                                                 else
                                                                                                 {
                                                                                                     if (GenHorasCita.class == item.getClass())
                                                                                                    {
                                                                                                      SihicApp.genHorasCita= (GenHorasCita) item;
                                                                                                    }
                                                                                                     else
                                                                                                     {
                                                                                                         if (AgnMedicos.class == item.getClass())
                                                                                                        {
                                                                                                            SihicApp.agnMedicos= (AgnMedicos) item;
                                                                                                        }
                                                                                                         else
                                                                                                         {
                                                                                                             if (AgnConsultorios.class == item.getClass())
                                                                                                             {
                                                                                                               SihicApp.agnConsultorios= (AgnConsultorios) item;
                                                                                                              }
                                                                                                             else
                                                                                                             {
                                                                                                                 if (Sucursales.class == item.getClass())
                                                                                                 {
                                                                                                    SihicApp.sucursales= (Sucursales) item;
                                                                                                 }
                                                                                                             }
                                                                                                         }
                                                                                                     }
                                                                                                 }
                                                                                               }
                                                                                             }
                                                                                        }
                                                                                    }
                                                                                  }
                                                                              }
                                                                          }
                                                                      }
                                                                 }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        popover.hide();
        // pageBrowser.goToPage(item.getEnsemblePath());
    }

    @Override
    public void setPopover(Popover popover) {
        this.popover = popover;
    }

    @Override
    public Popover getPopover() {
        return popover;
    }

    @Override
    public Node getPageNode() {
        return this;
    }

    @Override
    public String getPageTitle() {
        return "";
    }

    @Override
    public String leftButtonText() {
        return null;
    }

    @Override
    public void handleLeftButton() {
    }

    @Override
    public String rightButtonText() {
        return null;
    }

    @Override
    public void handleRightButton() {
    }

    @Override
    public void handleShown() {
    }

    @Override
    public void handleHidden() {
    }

    @Override
    public ListCell<Object> call(ListView<Object> p) {
        return new SearchResultListCell();
    }

    private class IconPane extends Pane {

        private Region samplesIcon = new Region();
        private Label classesIcon = new Label("C");
        private Label propertiesIcon = new Label("P");
        private Label methodsIcon = new Label("M");
        private Label fieldsIcon = new Label("F");
        private Label enumsIcon = new Label("E");
        private Region documentationIcon = new Region();
        private List<SearchResultListCell> allCells = new ArrayList<>();
        private Rectangle classesLine = new Rectangle(0, 0, 40, 1);
        private Rectangle propertiesLine = new Rectangle(0, 0, 40, 1);
        private Rectangle methodsLine = new Rectangle(0, 0, 40, 1);
        private Rectangle fieldsLine = new Rectangle(0, 0, 40, 1);
        private Rectangle enumsLine = new Rectangle(0, 0, 40, 1);
        private Rectangle documentationLine = new Rectangle(0, 0, 40, 1);

        public IconPane() {
            getStyleClass().add("search-icon-pane");
            samplesIcon.getStyleClass().add("soluciones-icon");
            documentationIcon.getStyleClass().add("documentation-icon");
            classesLine.setFill(Color.web("#dfdfdf"));
            propertiesLine.setFill(Color.web("#dfdfdf"));
            methodsLine.setFill(Color.web("#dfdfdf"));
            fieldsLine.setFill(Color.web("#dfdfdf"));
            enumsLine.setFill(Color.web("#dfdfdf"));
            documentationLine.setFill(Color.web("#dfdfdf"));
            // getChildren().addAll(samplesIcon,classesIcon,propertiesIcon,methodsIcon,fieldsIcon,enumsIcon,documentationIcon,
            //                   classesLine,propertiesLine,methodsLine,fieldsLine,enumsLine,documentationLine);
            setMouseTransparent(true);
        }

        @Override
        protected void layoutChildren() {
            List<SearchResultListCell> visibleCells = new ArrayList<>(20);
            for (SearchResultListCell cell : allCells) {
                if (cell.isVisible()) {
                    visibleCells.add(cell);
                }
            }
            Collections.sort(visibleCells, (Node o1, Node o2) -> Double.compare(o1.getLayoutY(), o2.getLayoutY()));

            samplesIcon.setLayoutX(8);
            samplesIcon.resize(24, 24);
            classesIcon.setLayoutX(8);
            classesIcon.resize(24, 24);
            propertiesIcon.setLayoutX(8);
            propertiesIcon.resize(24, 24);
            methodsIcon.setLayoutX(8);
            methodsIcon.resize(24, 24);
            fieldsIcon.setLayoutX(8);
            fieldsIcon.resize(24, 24);
            enumsIcon.setLayoutX(8);
            enumsIcon.resize(24, 24);
            documentationIcon.setLayoutX(8);
            documentationIcon.resize(24, 24);

            samplesIcon.setVisible(false);
            classesIcon.setVisible(false);
            propertiesIcon.setVisible(false);
            methodsIcon.setVisible(false);
            fieldsIcon.setVisible(false);
            enumsIcon.setVisible(false);
            documentationIcon.setVisible(false);
            classesLine.setVisible(false);
            propertiesLine.setVisible(false);
            methodsLine.setVisible(false);
            fieldsLine.setVisible(false);
            enumsLine.setVisible(false);
            documentationLine.setVisible(false);

            final int last = visibleCells.size() - 1;
            DocumentType lastDocType = null;
            /* for(int index = 0; index <= last; index ++) {
                SearchResultListCell cell = visibleCells.get(index);
                DocumentType docType = getDocumentTypeForCell(cell);
                if (lastDocType != docType && docType != null) {
                    // this is first of this doc type
                    Node icon = getIconForDocType(docType);
                    icon.setVisible(true);
                    // calculate cell position relative to iconPane
                    Point2D cell00 = cell.localToScene(0, 0);
                    cell00 = sceneToLocal(cell00);
                    // check if next is differnt
                    if (index != last && getDocumentTypeForCell(visibleCells.get(index+1)) != docType) {
                        icon.setLayoutY(cell00.getY()+8);
                    } else {
                        icon.setLayoutY(Math.max(8,cell00.getY()+8));
                    }
                    // update line
                    Rectangle line = getLineForDocType(docType);
                    if (line != null) {
                        line.setVisible(true);
                        line.setLayoutY(cell00.getY());
                    }
                }
                lastDocType = docType;
            }*/

        }

    }

    private class SearchResultListCell extends ListCell<Object> implements Skin<SearchResultListCell>, EventHandler {

        private static final int TEXT_GAP = 6;
        private ImageView arrow = new ImageView(RIGHT_ARROW);
        private Label title = new Label();
        private Label details = new Label();
        private int cellIndex;
        private Rectangle topLine = new Rectangle(0, 0, 1, 1);

        private SearchResultListCell() {
            super();
            //System.out.println("CREATED TimeSlot CELL " + (cellIndex));
            // we don't need any of the labeled functionality of the default cell skin, so we replace skin with our own
            // in this case using this same class as it saves memory. This skin is very simple its just a HBox container
            setSkin(this);
            getStyleClass().setAll("search-result-cell");
            title.getStyleClass().setAll("title");
            details.getStyleClass().setAll("details");
            // topLine.setFill(Color.web("#dfdfdf"));
            getChildren().addAll(title);
            setOnMouseClicked(this);

            // listen to changes of this cell being added and removed from list 
            // and when it or its parent is moved. If any of those things change
            // then update the iconPane's layout. requestLayout() will be called
            // many times for any change of cell layout in the list but that 
            // dosn't matter as they will all be batched up by layout machanisim
            // and iconPane.layoutChildren() will only be called once per frame.
            final ChangeListener<Bounds> boundsChangeListener = (ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) -> {
                iconPane.requestLayout();
            };
            parentProperty().addListener((ObservableValue<? extends Parent> ov, Parent oldParent, Parent newParent) -> {
                if (oldParent != null) {
                    oldParent.layoutBoundsProperty().removeListener(boundsChangeListener);
                }
                if (newParent != null && newParent.isVisible()) {
                    iconPane.allCells.add(SearchResultListCell.this);
                    newParent.layoutBoundsProperty().addListener(boundsChangeListener);
                } else {
                    iconPane.allCells.remove(SearchResultListCell.this);
                }
                iconPane.requestLayout();
            });
        }

        @Override
        protected double computeMinWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int) ((insets.getLeft() + title.minWidth(h) + TEXT_GAP + details.minWidth(h) + insets.getRight()) + 0.5d);
        }

        @Override
        protected double computePrefWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int) ((insets.getLeft() + title.prefWidth(h) + TEXT_GAP + details.prefWidth(h) + insets.getRight()) + 0.5d);
        }

        @Override
        protected double computeMaxWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int) ((insets.getLeft() + title.maxWidth(h) + TEXT_GAP + details.maxWidth(h) + insets.getRight()) + 0.5d);
        }

        @Override
        protected double computeMinHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int) ((insets.getTop() + title.minHeight(w) + TEXT_GAP + details.minHeight(w) + insets.getBottom()) + 0.5d);
        }

        @Override
        protected double computePrefHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int) ((insets.getTop() + title.prefHeight(w) + TEXT_GAP + details.prefHeight(w) + insets.getBottom()) + 0.5d);
        }

        @Override
        protected double computeMaxHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int) ((insets.getTop() + title.maxHeight(w) + TEXT_GAP + details.maxHeight(w) + insets.getBottom()) + 0.5d);
        }

        @Override
        protected void layoutChildren() {
            final Insets insets = getInsets();
            final double left = insets.getLeft();
            final double top = insets.getTop();
            final double w = getWidth() - left - insets.getRight();
            final double h = getHeight() - top - insets.getBottom();
            final double titleHeight = title.prefHeight(w);
            title.setLayoutX(left);
            title.setLayoutY(top);
            title.resize(w, titleHeight);
            final double detailsHeight = details.prefHeight(w);
            details.setLayoutX(left);
            details.setLayoutY(top + titleHeight + TEXT_GAP);
            details.resize(w, detailsHeight);
            final Bounds arrowBounds = arrow.getLayoutBounds();
            arrow.setLayoutX(getWidth() - arrowBounds.getWidth() - 12);
            arrow.setLayoutY((int) ((getHeight() - arrowBounds.getHeight()) / 2d));
            topLine.setLayoutX(left - 5);
            topLine.setWidth(getWidth() - left + 5);
        }

        // CELL METHODS
        @Override
        protected void updateItem(Object result, boolean empty) {
                super.updateItem(result, empty);
            if (result == null) { // empty item
                arrow.setVisible(false);
                title.setVisible(false);
                details.setVisible(false);
            } else {
                arrow.setVisible(true);
                title.setVisible(true);
                details.setVisible(true);
                if (Puc.class == result.getClass()) {
                    title.setText(((Puc) result).getCodigo() + " " + ((Puc) result).getNombre());
                    details.setText(((Puc) result).getDescripcion());
                } else {
                    if (ComprobanteCausacionEgreso.class == result.getClass()) {
                        title.setText(((ComprobanteCausacionEgreso) result).getProveedores().getNo_ident()+ "||" + ((ComprobanteCausacionEgreso) result).getProveedores().getNombre()+" "+((ComprobanteCausacionEgreso) result).getProveedores().getPrimerapellido()+ "||" + ((ComprobanteCausacionEgreso) result).getValor());
                        details.setText(((ComprobanteCausacionEgreso) result).getNocomprobantecerosizquierda());
                    } else {
                        if (GenEapb.class == result.getClass()) {
                            title.setText(((GenEapb) result).getNombre()+" Codigo:"+((GenEapb) result).getCodigo());
                            details.setText(((GenEapb) result).getCodigo());
                        } else {
                            if (HclCups.class == result.getClass()) {
                                title.setText(((HclCups) result).getCodigo() + "||" + ((HclCups) result).getDescripcion());
                                details.setText(((HclCups) result).getCodigo());
                            } else {
                                if (Producto.class == result.getClass()) {
                                    title.setText(((Producto) result).getCodigo() + "||" + ((Producto) result).getNombre());
                                    details.setText(((Producto) result).getCodigo());
                                } else {
                                    if (Kardex.class == result.getClass()) {
                                        title.setText(((Kardex) result).getProducto().getCodigo() + "||" + ((Kardex) result).getProducto().getNombre());
                                        details.setText(((Kardex) result).getProducto().getCodigo());
                                    } else {
                                        if (GenMunicipios.class == result.getClass()) {
                                            title.setText(((GenMunicipios) result).getNombre() + "-" + ((GenMunicipios) result).getGenDepartamentos().getNombre());
                                            details.setText(((GenMunicipios) result).getNombre());
                                        } else {
                                            if (GenProfesiones.class == result.getClass()) {
                                                title.setText(((GenProfesiones) result).getCodigo() + "-" + ((GenProfesiones) result).getDescripcion());
                                                details.setText(((GenProfesiones) result).getDescripcion());
                                            } else {
                                                if (Factura.class == result.getClass()) {
                                                    if (((Factura) result).getPrefijo().equals("A") || ((Factura) result).getPrefijo().equals("P")) {
                                                        try {
                                                            title.setText("No. Fact: " + ((Factura) result).getNofacturacerosizquierda() + " || " + ((Factura) result).getGenEapb().getNombre() + " || " + ((Factura) result).getGenTiposUsuarios().getNombre());
                                                            details.setText(((Factura) result).getNofacturacerosizquierda());
                                                        } catch (Exception e) {
                                                            title.setText("No. Fact: " + ((Factura) result).getNofacturacerosizquierda());
                                                            details.setText(((Factura) result).getNofacturacerosizquierda());

                                                        }
                                                    } else {
                                                        try {
                                                            title.setText("No. Fact: " + ((Factura) result).getNofacturacerosizquierda() + " ||" + " Usuario: " + ((Factura) result).getFacturaItems().get(0).getGenPacientes().getGenPersonas().getNombres());
                                                            details.setText(((Factura) result).getNofacturacerosizquierda());
                                                        } catch (Exception e) {
                                                            title.setText("No. Fact: " + ((Factura) result).getNofacturacerosizquierda());
                                                            details.setText(((Factura) result).getNofacturacerosizquierda());

                                                        }

                                                    }
                                                } else {
                                                    if (FacturaItem.class == result.getClass()) {
                                                        title.setText("No. Comp: " + ((FacturaItem) result).getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString() + "||" + "Procedimiento: " + ((FacturaItem) result).getProducto().getCodigo() + " " + ((FacturaItem) result).getProducto().getNombre());
                                                        details.setText(((FacturaItem) result).getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());
                                                    } else {
                                                        if (HclCie10.class == result.getClass()) {
                                                            title.setText(((HclCie10) result).getCodigo() + "||" + "Diagnóstico: " + ((HclCie10) result).getDescripcion());
                                                            details.setText("");
                                                        } else {
                                                            if (ActividadesEconomicas.class == result.getClass()) {
                                                                title.setText(((ActividadesEconomicas) result).getCodigo_ciiu_0079() + "||" + ((ActividadesEconomicas) result).getDescripcion());
                                                                details.setText("");
                                                            } else {
                                                                if (RetencionFuente.class == result.getClass()) {
                                                                    title.setText(((RetencionFuente) result).getConcepto() + "||" + ((RetencionFuente) result).getConcepto());
                                                                    details.setText("");
                                                                } else {
                                                                    if (FacturaProveedores.class == result.getClass()) {
                                                                        title.setText("N° Doc: " + ((FacturaProveedores) result).getNo_factura() + " || N° Ident: " + ((FacturaProveedores) result).getProveedores().getNo_ident() + " Nombre: " + ((FacturaProveedores) result).getProveedores().getNombre() + " Saldo: " + ((FacturaProveedores) result).getTotalAmount().subtract(((FacturaProveedores) result).getValor_egresos()).toEngineeringString());
                                                                        details.setText("");
                                                                    }
                                                                    else
                                                                    {
                                                                         if (GenPacientes.class == result.getClass()) {
                                                                        title.setText("N° Doc: " + ((GenPacientes) result).getGenPersonas().getDocumento()+" Nombre: "+((GenPacientes) result).getGenPersonas().getNombres() );
                                                                        details.setText("");
                                                                    }
                                                                    else
                                                                         {
                                                                        if (GenTiposDocumentos.class == result.getClass()) {
                                                                        title.setText("Sigla: " + ((GenTiposDocumentos) result).getSigla()+" Tipo Doc: "+((GenTiposDocumentos) result).getNombre());
                                                                        details.setText("");
                                                                    }
                                                                        else
                                                                        {
                                                                             if (GenSexo.class == result.getClass()) {
                                                                        title.setText(((GenSexo) result).getDescripcion());
                                                                        details.setText("");
                                                                    }
                                                                             else
                                                                             {
                                                                                 if (GenEtnias.class == result.getClass()) 
                                                                                 {
                                                                                   title.setText(((GenEtnias) result).getNombre());
                                                                                   details.setText("");
                                                                                 } 
                                                                                 else
                                                                                 {
                                                                                     if (GenNivelesEducativos.class == result.getClass()) 
                                                                                    {
                                                                                     title.setText(((GenNivelesEducativos) result).getDescripcion());
                                                                                     details.setText("");
                                                                                    }
                                                                                     else
                                                                                     {
                                                                                    if (GenZonaResidencia.class == result.getClass()) 
                                                                                    {
                                                                                     title.setText(((GenZonaResidencia) result).getDescripcion());
                                                                                     details.setText("");
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        if (GenEstadosCiviles.class == result.getClass()) 
                                                                                        {
                                                                                           title.setText(((GenEstadosCiviles) result).getNombre());
                                                                                           details.setText("");
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                          if (GenTiposAfiliacion.class == result.getClass()) 
                                                                                        {
                                                                                           title.setText(((GenTiposAfiliacion) result).getDescripcion());
                                                                                           details.setText("");
                                                                                        } 
                                                                                          else
                                                                                          {
                                                                                            if (GenTiposUsuarios.class == result.getClass()) 
                                                                                        {
                                                                                           title.setText(((GenTiposUsuarios) result).getNombre());
                                                                                           details.setText("");
                                                                                        }  
                                                                                            else
                                                                                            {
                                                                                                 if (GenNivelesUsuarios.class == result.getClass()) 
                                                                                                {
                                                                                                 title.setText(String.valueOf((((GenNivelesUsuarios) result).getNivel())));
                                                                                                 details.setText("");
                                                                                                 }
                                                                                                 else
                                                                                                 {
                                                                                                     if (GenConvenios.class == result.getClass()) 
                                                                                                     {
                                                                                                       title.setText(String.valueOf((((GenConvenios) result).getNumerocontrato()+" %: ")+((GenConvenios) result).getPorcentajedescuento()));
                                                                                                       details.setText("");
                                                                                                     }
                                                                                                     if (GenHorasCita.class == result.getClass()) 
                                                                                                     {
                                                                                                       title.setText(((GenHorasCita) result).getHora()+":"+((GenHorasCita) result).getMinutos()+" "+(((GenHorasCita) result).getZona()==0?"AM":"PM"));
                                                                                                       details.setText("");
                                                                                                     }
                                                                                                     else
                                                                                                     {
                                                                                                       if (AgnMedicos.class == result.getClass()) 
                                                                                                     {
                                                                                                       title.setText(((AgnMedicos) result).getGenPersonas().getNombres());
                                                                                                       details.setText("");
                                                                                                     } 
                                                                                                       else
                                                                                                       {
                                                                                                           if (AgnConsultorios.class == result.getClass()) 
                                                                                                         {
                                                                                                           title.setText(((AgnConsultorios) result).getDescripcion());
                                                                                                           details.setText("");
                                                                                                         }
                                                                                                           else
                                                                                                           {
                                                                                                               if (Sucursales.class == result.getClass()) 
                                                                                                               {
                                                                                                                 title.setText(((Sucursales) result).getNombre());
                                                                                                                  details.setText("");
                                                                                                               }
                                                                                                               else
                                                                                                               {
                                                                                                                 if (String.class == result.getClass()) 
                                                                                                                 {
                                                                                                                  title.setText(((String) result));
                                                                                                                  details.setText("");
                                                                                                                 } 
                                                                                                                 else
                                                                                                                 {
                                                                                                                     if (Proveedores.class == result.getClass()) 
                                                                                                                   {
                                                                                                                     title.setText(((Proveedores) result).getNo_ident()+" || "+((Proveedores) result).getNombre());
                                                                                                                     details.setText("");
                                                                                                                    }
                                                                                                                     else
                                                                                                                     {
                                                                                                                         if (AgnCitas.class == result.getClass()) 
                                                                                                                   {
                                                                                                                     title.setText(sihic.util.UtilDate.s_formatoyearmesdia(((AgnCitas) result).getFechaHora())+" || "+ ((AgnCitas) result).getGenHorasCita().getHora()+":"+((AgnCitas) result).getGenHorasCita().getMinutos()+"||"+((AgnCitas) result).getGenPacientes().getGenPersonas().getNombres());
                                                                                                                     details.setText("");
                                                                                                                    }
                                                                                                                     }
                                                                                                                 }
                                                                                                               }
                                                                                                           }
                                                                                                       }
                                                                                                     }
                                                                                                 }
                                                                                            }
                                                                                          }
                                                                                        }
                                                                                    }
                                                                                     }
                                                                                 }
                                                                             }
                                                                        }
                                                                         }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        // SKIN METHODS
        @Override
        public SearchResultListCell getSkinnable() {
            return this;
        }

        @Override
        public Node getNode() {
            return null;
        }

        @Override
        public void dispose() {
        }

        @Override
        public void handle(Event t) {
            itemClicked(getItem());

        }
    }
}
