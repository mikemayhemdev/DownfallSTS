package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;

import static champ.ChampMod.*;

public class GladiatorsBookOfMartialProwess extends CustomRelic {

    public static final String ID = ChampMod.makeID("GladiatorsBookOfMartialProwess");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GladiatorsHandbook.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GladiatorsHandbook.png"));


    public GladiatorsBookOfMartialProwess() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {

        if (c.hasTag(FINISHER)) {

            if (AbstractDungeon.cardRandomRng.randomBoolean()) {

                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            } else {
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));

            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
