package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CrookedStrike extends AbstractChampCard {

    public final static String ID = makeID("CrookedStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    public CrookedStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount >= 20) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
            }
        }
        finisher();
    }

    public void upp() {
        upgradeDamage(3);
    }


    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount >= 20) {
                glowColor = GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        glowColor = BLUE_BORDER_GLOW_COLOR;
    }
}