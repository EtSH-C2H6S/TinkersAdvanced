package com.c2h6s.tinkers_advanced.client.book;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.client.book.transformer.BookTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ModifierSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ModifierTagInjectorTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ToolSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ToolTagInjectorTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.materials.TierRangeMaterialSectionTransformer;

public class TiAcBookData extends BookData {
    public static final BookData ULTRA_DENSE_BOOK = BookLoader.registerBook( TinkersAdvanced.getLocation("ultra_dense_book"),false,false);
    public static void intiBook(){
        ULTRA_DENSE_BOOK.addTransformer(ToolTagInjectorTransformer.INSTANCE);
        ULTRA_DENSE_BOOK.addTransformer(ModifierTagInjectorTransformer.INSTANCE);
        ULTRA_DENSE_BOOK.addTransformer(ToolSectionTransformer.INSTANCE);
        ModifierSectionTransformer GENERAL_SPECIAL = new ModifierSectionTransformer("general");
        ModifierSectionTransformer GENERATOR_TRANSFORMER = new ModifierSectionTransformer("generator_modifier");
        ULTRA_DENSE_BOOK.addRepository(new FileRepository(new ResourceLocation(TinkersAdvanced.MODID, "book/" + "ultra_dense_book")));
        ULTRA_DENSE_BOOK.addTransformer(GENERAL_SPECIAL);
        ULTRA_DENSE_BOOK.addTransformer(GENERATOR_TRANSFORMER);
        ULTRA_DENSE_BOOK.addTransformer(BookTransformer.indexTranformer());
        ULTRA_DENSE_BOOK.addTransformer(TierRangeMaterialSectionTransformer.INSTANCE);
        ULTRA_DENSE_BOOK.addTransformer(BookTransformer.paddingTransformer());
    }
}
