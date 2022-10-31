package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class BlackKnightsHelmet extends CustomRelic {

    public static final String ID = ChampMod.makeID("BlackKnightHelmet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WarlordsHelmet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WarlordsHelmet.png"));

    public BlackKnightsHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    boolean didDef = false;
    boolean didBers = false;

    @Override
    public void atBattleStart() {
        didDef = false;
        didBers = false;
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        switch (newStance.ID) {
            case DefensiveStance.STANCE_ID:
                if (!didDef) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2), 2));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -1), -1));
                    didDef = true;
                }
                break;
            case BerserkerStance.STANCE_ID:
                if (!didBers) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -1), -1));
                    didBers = true;
                }
                break;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
