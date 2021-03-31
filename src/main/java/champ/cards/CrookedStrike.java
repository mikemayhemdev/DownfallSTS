package champ.cards;

import champ.ChampMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class CrookedStrike extends AbstractChampCard {

    public final static String ID = makeID("CrookedStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 3;

    public CrookedStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.FINISHER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

        finisher();
    }

    public void upp() {
        upgradeBaseCost(1);
    }


}