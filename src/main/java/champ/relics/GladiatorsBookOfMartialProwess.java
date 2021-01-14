package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static champ.ChampMod.*;

public class GladiatorsBookOfMartialProwess extends CustomRelic {

    public static final String ID = ChampMod.makeID("GladiatorsBookOfMartialProwess");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GladiatorsHandbook.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GladiatorsHandbook.png"));

    private boolean hasPlayedOpener;
    private boolean hasPlayedTechnique;
    private boolean hasPlayedCombo;
    private boolean hasPlayedFinisher;

    public GladiatorsBookOfMartialProwess() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(OPENER)) {
            hasPlayedOpener = true;
        }
        if (c.hasTag(TECHNIQUE)) {
            hasPlayedTechnique = true;
        }
        if (c.hasTag(FINISHER)) {
            hasPlayedFinisher = true;
        }
        if (c.hasTag(COMBO)) {
            hasPlayedCombo = true;
        }
        if (hasPlayedFinisher && hasPlayedCombo && hasPlayedTechnique && hasPlayedOpener) {

            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));

            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));

            hasPlayedOpener = false;
            hasPlayedTechnique = false;
            hasPlayedFinisher = false;
            hasPlayedCombo = false;
        }
    }

    @Override
    public void atTurnStart() {
        hasPlayedOpener = false;
        hasPlayedTechnique = false;
        hasPlayedFinisher = false;
        hasPlayedCombo = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
