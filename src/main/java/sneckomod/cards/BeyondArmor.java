package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import downfall.util.CommonAttackReward;
import downfall.util.CommonSkillReward;
import sneckomod.SneckoMod;

public class BeyondArmor extends AbstractSneckoCard implements OnObtainCard{

    public final static String ID = makeID("BeyondArmor");

    private static final int BLOCK = 6;  // Base block
    private static final int UPG_BLOCK = 2;  // Upgrade block amount

    public BeyondArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;  // Set the base block amount
        SneckoMod.loadJokeCardImage(this, "BeyondArmor.png");  // Load card image
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            AbstractCard lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1);
            if (lastCard.color != this.color) {
                blck();
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
            upgradeBlock(UPG_BLOCK);  // Upgrade block amount
        }
    }
}
