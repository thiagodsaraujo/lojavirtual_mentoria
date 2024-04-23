package dev.mentoria.lojavirtual_mentoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void softExclusaoVenda(Long idVenda) {
        var sql = "begin; UPDATE vd_cp_loja_virt SET excluido = true WHERE id = " + idVenda + "; commit;";

        jdbcTemplate.execute(sql);
    }




    // Usou JDBC do string pois nao estava conseguindo fazer a exclusao total da venda através do repository
    public void exclusaoTotalVendaBanco(Long idVenda) {

        String value =

                          " begin;"
                        + " UPDATE nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id = "+idVenda+"; "
                        + " delete from nota_fiscal_venda where venda_compra_loja_virtual_id = "+idVenda+"; "
                        + " delete from item_venda_loja where venda_compra_loja_virtual_id = "+idVenda+"; "
                        + " delete from status_rastreio where venda_compra_loja_virtual_id = "+idVenda+"; "
                        + " delete from vd_cp_loja_virt where id = "+idVenda+"; "
                        + " commit; ";

        jdbcTemplate.execute(value);
    }


    public void ativaRegistroVendaBanco(Long idVenda) {

        String value = "begin; UPDATE vd_cp_loja_virt SET excluido = false WHERE id = " + idVenda + "; commit;";

        jdbcTemplate.execute(value);
    }
}
