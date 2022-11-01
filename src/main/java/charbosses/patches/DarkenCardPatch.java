package charbosses.patches;


import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch(clz = AbstractCard.class, method = "renderTint")

public class DarkenCardPatch {

    private static final Color lightenColor = new Color(255F * .43F, 255F * .37F, 255F * .65F, 0F);
    private static final Color darkenColor = new Color(0F, 0F, 0F, .75F);

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(AbstractCard instance, SpriteBatch sb) {

        if (instance instanceof AbstractBossCard) {
            if (!Settings.hideCards) {
                Color tintColor;
                if (((AbstractBossCard) instance).bossDarkened && !((AbstractBossCard) instance).hov2) {
                    tintColor = darkenColor;
                } else {
                    tintColor = lightenColor;
                }
                TextureAtlas.AtlasRegion cardBgImg = instance.getCardBgAtlas();
                AbstractBossCard cB = (AbstractBossCard) instance;
                if (cardBgImg != null) {
                    cB.renderHelperB(sb, tintColor, cardBgImg, instance.current_x, instance.current_y);
                } else {
                    cB.renderHelperB(sb, tintColor, instance.getCardBg(), instance.current_x - 256.0F, instance.current_y - 256.0F);
                }
            }
            return SpireReturn.Return(null);
        }

        return SpireReturn.Continue();
    }
}


