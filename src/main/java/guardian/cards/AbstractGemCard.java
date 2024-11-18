package guardian.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;
import guardian.GuardianMod;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGemCard extends AbstractGuardianCard {
    //private String[] descriptorStrings = CardCrawlGame.languagePack.getUIString(GuardianMod.makeID("gemDescriptor")).TEXT;

    public AbstractGemCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        /*
        if (downfallMod.disableDescriptors && !this.rawDescription.contains(descriptorStrings[1])) {
            //If zht & zhs & kor need/needn't add in end you can add there if()
            this.rawDescription = descriptorStrings[1] + this.rawDescription;
            initializeDescription();
        }
    }

    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        if (!downfallMod.disableDescriptors) {
            tags.add(descriptorStrings[0]);
        }
        return tags;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (!downfallMod.disableDescriptors && !keywords.contains(("guardianmod:" + descriptorStrings[0]).toLowerCase())) {
            tips.add(new TooltipInfo(BaseMod.getKeywordTitle("guardianmod:gem"), BaseMod.getKeywordDescription("guardianmod:gem")));
        }
        return tips;
         */
    }
}
