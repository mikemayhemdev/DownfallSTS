package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import downfall.util.CommonSkillReward;
import downfall.util.UpgradeCardReward;
import sneckomod.SneckoMod;

public class OtherworldlyStrike extends AbstractSneckoCard implements OnObtainCard{

    public final static String ID = makeID("OtherworldlyStrike");

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public OtherworldlyStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "OtherworldlyStrike.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal initial damage
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);

        // Check if the last played card was a different color
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            AbstractCard lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1);

            // If the last card's color is different from this card's color, deal additional damage
            if (lastCard.color != this.color) {
                dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
            }
        }
    }


    @Override
    public void onObtainCard() {
        AbstractDungeon.getCurrRoom().rewards.add(new CommonSkillReward());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
