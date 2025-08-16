package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.util.TextureLoader;
import hermit.cards.Malice;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class DuelingGlove extends CustomRelic {

    public static final String ID = ChampMod.makeID("DuelingGlove");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DuelingGlove.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DuelingGlove.png"));

    public DuelingGlove() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        System.out.println(c.type);
        //Malice is a "single target" card and the Vulnerable will even come BEFORE the card deals damage. TWO bugs!
        if (!(c instanceof Malice)) {
            if (m != null) {
                if (!m.hasPower(VulnerablePower.POWER_ID) && (c.target == AbstractCard.CardTarget.ENEMY || c.target == AbstractCard.CardTarget.SELF_AND_ENEMY) && c.type == AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, 1, false), 1));
                }
            }
        }
    }

    public void onTrigger(AbstractCreature target) {
            if (target != null) {
                if (!target.hasPower(VulnerablePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
